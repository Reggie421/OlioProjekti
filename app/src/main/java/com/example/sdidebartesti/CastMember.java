package com.example.sdidebartesti;

import androidx.annotation.Nullable;

public class CastMember {
    String firstName;
    String lastName;
    String role;

    public void Actor(String fName, @Nullable String lName, String r){
        firstName = fName;
        lastName = lName;
        role = r;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String role(){
        return role;
    }
}
