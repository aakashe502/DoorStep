package com.hadIt.doorstep.cache.model;

public class Users {
    public String userName, emailId, password, mobile, profilePhoto, uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Users(String userName, String emailId, String password, String mobile, String profilePhoto, String uid) {
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.mobile = mobile;
        this.profilePhoto = profilePhoto;
        this.uid = uid;
    }

    public Users() {
    }

    @Override
    public Users clone() {
        return new Users();
    }
}
