package com.entity;

public class Administrator {
    private String userName;
    private String nickname;
    private String password;
    private String verifyRange;

    public void setId(String userName) {
        this.userName = userName;
    }
    public void setAdministrator(String nickname){
        this.nickname = nickname;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setVerifyRange(String verifyRange){
        this.verifyRange = verifyRange;
    }
    public String getId() {
        return userName;
    }
    public String getAdministrator(){
        return nickname;
    }
    public String getPassword(){
        return password;
    }
    public String getVerifyRange(){
        return verifyRange;
    }
}
