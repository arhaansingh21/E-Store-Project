package com.estore.api.estoreapi.model;

public abstract class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    /**
    * Gets id of User 
    */
    public String getId() {
        return this.id;
    }
    
    public boolean login(String idString){
        return idString.equals(id);        
    } 
}