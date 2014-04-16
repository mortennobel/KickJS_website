package org.kickjs.shadereditor;

import com.google.appengine.api.datastore.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.kickjs.shared.JSONRequest;
import org.kickjs.shared.LoginInfo;
import org.kickjs.shared.URLShortener;
import org.kickjs.shared.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Update a given shader or create a new shader
 */
public class UpdateShader extends JSONRequest {
    @Override
    protected JSONObject doPost(JSONObject jsonRequest, HttpServletRequest req) throws JSONException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        JSONObject response = new JSONObject();
        User user = LoginInfo.getUser(req);
        if (user == null){
            response.put("message", "User not logged in");
            return response;
        }
        String name = jsonRequest.getString("name");
        Date lastModified = new Date();
        String id = jsonRequest.has("id")?jsonRequest.getString("id"):null;
        if (id != null &&id.length()==0){
            id = null;
        }
        String owner = jsonRequest.getString("owner"); // not currently used
        String data = jsonRequest.getString("data");
        try{
            Entity entity;
            Shader s;
            if (id==null){
                entity = new Entity(Shader.ENTITY_NAME,name,user.getKey());
                s = new Shader();
                s.updateEntity(entity);
            } else {
                entity = datastore.get(KeyFactory.stringToKey(id));
                s = new Shader(entity);
                if (!s.getOwner().equals(user.getKey())){
                    response.put("message", "Wrong user");
                    return response;
                }
            }
            if (s.getShortURL()==null ||s.getShortURL().length()==0){
                try{
                    String longURL = "http://"+req.getServerName()+"/tool/shader_editor/shader_editor.html#"+ KeyFactory.keyToString(entity.getKey());
                    String shortURL = URLShortener.getShortURL(longURL);
                    s.setShortURL(shortURL);
                } catch (Exception e){
                    log("",e);
                }
            }
            s.setName(name);
            s.setLastModified(lastModified);
            s.setData(new Text(data));
            s.updateEntity(entity);
            datastore.put(entity);
            response.put("message", "Saved successfully");
            response.put("id", KeyFactory.keyToString(entity.getKey()));
            response.put("shortUrl", String.valueOf(s.getShortURL()));
        } catch (EntityNotFoundException e){
            response.put("message", "Error during save");
        }
        return response;
    }
}
