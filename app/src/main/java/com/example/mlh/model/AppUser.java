package com.example.mlh.model;


public class AppUser {

    String firstname, lastname, useremail, usermobilenumber, userpasscode;

    public AppUser(String firstname, String lastname, String useremail, String usermobilenumber, String userpasscode){
        this.firstname = firstname;
        this.lastname = lastname;
        this.useremail = useremail;
        this.usermobilenumber = usermobilenumber;
        this.userpasscode = userpasscode;
    }

    public String getUserpasscode() {
        return userpasscode;
    }

    public void setUserpasscode(String userpasscode) {
        this.userpasscode = userpasscode;
    }

    public String getFirstname(){
        return firstname;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUseremail(){
        return useremail;
    }
    public void setUseremail(String useremail){
        this.useremail = useremail;
    }

    public String getUsermobilenumber(){
        return usermobilenumber;
    }
    public void setUsermobilenumber(String usermobilenumber){
        this.usermobilenumber = usermobilenumber;
    }


}