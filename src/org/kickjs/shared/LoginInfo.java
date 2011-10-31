package org.kickjs.shared;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.sun.deploy.net.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.Date;

public class LoginInfo extends JSONRequest {
    protected JSONObject doGet(JSONObject jsonRequest, HttpServletRequest req) throws JSONException {
        JSONObject response = new JSONObject();
        System.out.println("URLShortener.URL_SHORTENER_API_KEY "+URLShortener.URL_SHORTENER_API_KEY);


        String thisURL = req.getRequestURI();
        if (req.getParameter("url")!=null){
            thisURL = req.getParameter("url");
        }
        UserService userService = UserServiceFactory.getUserService();
        User user = getUser(req);
        if (user==null){
            response.put("loginURL",userService.createLoginURL(thisURL));
        } else {
            try {
                response.put("logoutURL","/Logout?url="+ URLEncoder.encode(thisURL,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.put("loginCount",user.getLoginCount());
            response.put("username",user.getUserPricipal());
        }
        return response;
    }

    public static User getUser(HttpServletRequest req){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null){
            return user;
        }
        Principal userPrincipal = req.getUserPrincipal();
        System.out.println("User principal "+userPrincipal);
        if (userPrincipal == null){
            return null;
        }
        String userPrincipalName = userPrincipal.getName();
        Key key = KeyFactory.createKey(User.ENTITY_NAME,userPrincipalName);
        try {
            Entity entity = datastore.get(key);
            if (entity != null){
                user = new User(entity);
            }
        } catch (EntityNotFoundException e){
            // ignore
        }
        if (user == null){
            user = new User(userPrincipalName);
            System.out.println("Create new user "+userPrincipal);
        } else {
            user.setLastLogin(new Date());
            user.increamentLoginCount();
            System.out.println("Found user in db "+userPrincipal);
        }
        datastore.put(user.createEntity());
        session.setAttribute("user",user);
        return user;
    }

}
