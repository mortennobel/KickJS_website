package org.kickjs.shadereditor;


import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import java.util.Date;

public class Shader {
    public static final String ENTITY_NAME = "org.kickjs.shared.Shader";

    private Key key;

    private Date lastModified;

    private Key owner;

    private String name;

    private String shortURL;

    private Text data;

    public Shader(){

    }

    public Shader(Entity entity){
        owner = entity.getParent();
        name = (String)entity.getProperty("name");
        shortURL = (String)entity.getProperty("shortURL");
        data = (Text) entity.getProperty("data");
        lastModified = (Date)entity.getProperty("lastModified");
    }

    public void updateEntity (Entity entity){
        entity.setProperty("name",name);
        entity.setProperty("data", data);
        entity.setProperty("shortURL",shortURL);
        entity.setProperty("lastModified",lastModified);
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Key getOwner() {
        return owner;
    }

    public void setOwner(Key owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Text getData() {
        return data;
    }

    public void setData(Text data) {
        this.data = data;
    }

    public String getShortURL() {
        return shortURL;
    }

    public void setShortURL(String shortURL) {
        this.shortURL = shortURL;
    }
}
