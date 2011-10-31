package org.kickjs.test;

import org.json.JSONException;
import org.json.JSONObject;
import org.kickjs.shared.JSONRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Test of JSONRequest
 */
public class JSONTest extends JSONRequest{
    protected JSONObject doGet(JSONObject jsonRequest, HttpServletRequest req) throws JSONException {
        JSONObject response = new JSONObject();
        if (jsonRequest.has("name")){
            response.put("message", "Hello " + jsonRequest.get("name"));
        } else {
            response.put("message", "Hello world");
        }

        if (jsonRequest.has("session")){
            boolean hasSession = req.getSession(false)!=null;
            response.put("session", "Has Session: " + hasSession);
            if (hasSession){
                Object sessionValue = req.getSession().getAttribute("name");
                response.put("sessionValue", sessionValue);
            }
        }
        if (jsonRequest.has("createSession")){
            HttpSession session = req.getSession();
            session.setAttribute("name", "" + System.currentTimeMillis());
        }



        return response;
    }

    protected JSONObject doPost(JSONObject jsonRequest, HttpServletRequest req) throws JSONException {
        JSONObject res = new JSONObject();
        if (jsonRequest.has("name")){
            res.put("message", "Hello "+ jsonRequest.get("name"));
        } else {
            res.put("message", "Hello world");
        }
        return res;
    }
}
