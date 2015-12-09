package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class UserInformation {
    private String userName;
    private String userAge;
    private String userGender;
    private String userRelationshipStatus;
    private byte[] userPic;
    private String userPhoneNo;
    private String userEmail;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserRelationshipStatus() {
        return userRelationshipStatus;
    }

    public byte[] getUserPic() {
        return userPic;
    }

    public String getUserPhoneNo() {
        return userPhoneNo;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserRelationshipStatus(String userRelationshipStatus) {
        this.userRelationshipStatus = userRelationshipStatus;
    }

    public void setUserPic(Bitmap userPic) {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        if (userPic!=null) {
            userPic.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            this.userPic = byteArrayOutputStream.toByteArray();
        }
    }

    public void setUserPhoneNo(String userPhoneNo) {
        this.userPhoneNo = userPhoneNo;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
