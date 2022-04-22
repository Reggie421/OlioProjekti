package com.example.sdidebartesti;

import androidx.annotation.Nullable;

public class CastMember {
    String firstName;
    String lastName;

    public CastMember(String fName, String lName){
        firstName = fName;
        lastName = lName;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
}
