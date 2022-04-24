package com.example.sdidebartesti;

public class CastMember {
    String firstName;
    String lastName;
    String role;

    public CastMember(String fName, String lName, String r){
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
    public String getRole(){
        return role;
    }

}
