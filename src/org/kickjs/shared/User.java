package org.kickjs.shared;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.sun.deploy.net.HttpRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * Represent a user on the site
 */
public class User implements Serializable {
    public static final String ENTITY_NAME = "org.kickjs.shared.User";

    private String userPricipal;

    private long loginCount;

    private Date lastLogin;

    private Date lastLike;

    public User(String userPricipal) {
        this.userPricipal = userPricipal;
        lastLike = new Date(0L);
        lastLogin = new Date();
    }

    public User(Entity entity){
        userPricipal = (String)entity.getProperty("userPricipal");
        loginCount = (Long)entity.getProperty("loginCount");
        lastLike = (Date)entity.getProperty("lastLike");
        lastLogin = (Date)entity.getProperty("lastLogin");
    }

    public Entity createEntity(){
        Entity entity = new Entity(ENTITY_NAME, userPricipal);
        entity.setProperty("userPricipal",userPricipal);
        entity.setProperty("loginCount",loginCount);
        entity.setProperty("lastLike",lastLike);
        entity.setProperty("lastLogin",lastLogin);
        return entity;
    }

    public Key getKey(){
        return KeyFactory.createKey(ENTITY_NAME,userPricipal);
    }

    public long getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLike() {
        return lastLike;
    }

    public void setLastLike(Date lastLike) {
        this.lastLike = lastLike;
    }

    public void increamentLoginCount(){
        loginCount++;
    }

    public String getUserPricipal() {
        return userPricipal;
    }

    public void setUserPricipal(String userPricipal) {
        this.userPricipal = userPricipal;
    }
}
