package org.kickjs.shadereditor;

import com.google.appengine.api.datastore.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.kickjs.shared.JSONRequest;
import org.kickjs.shared.LoginInfo;
import org.kickjs.shared.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetShader extends JSONRequest {
    @Override
    protected JSONObject doGet(JSONObject jsonRequest, HttpServletRequest req) throws JSONException {
        JSONObject response = new JSONObject();

        User user = LoginInfo.getUser(req);
        if (jsonRequest.has("id")){
            getShader(jsonRequest, response);
        } else if (user!=null){
            getShaderList(response, user);
        }
        return response;
    }

    private void getShaderList(JSONObject response, User user) throws JSONException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query q = new Query(Shader.ENTITY_NAME,user.getKey());
        q.addSort("lastModified");
        PreparedQuery pq = datastore.prepare(q);
        for (Entity result : pq.asIterable()){
            String name = result.getProperty("name").toString();
            String id = KeyFactory.keyToString(result.getKey());
            JSONObject object = new JSONObject();
            object.put("name",name);
            object.put("id",id);
            response.append("shaderList",object);
        }
    }

    private void getShader(JSONObject jsonRequest, JSONObject response) throws JSONException {
        String shaderid = jsonRequest.getString("id");

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try{
            Entity entity = datastore.get(KeyFactory.stringToKey(shaderid));
            Shader shader = new Shader(entity);
            response.put("name",shader.getName());
            response.put("data",shader.getData().getValue());
            response.put("lastModified",shader.getLastModified().toString());
            response.put("id",shaderid);
            response.put("owner",KeyFactory.keyToString(shader.getOwner()));
            response.put("shortUrl", String.valueOf(shader.getShortURL()));
        } catch (EntityNotFoundException e){
            response.put("message","Not found");
        }
    }
}
