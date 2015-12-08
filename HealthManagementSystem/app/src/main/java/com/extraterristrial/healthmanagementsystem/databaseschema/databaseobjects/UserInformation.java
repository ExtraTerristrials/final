package com.extraterristrial.healthmanagementsystem.databaseschema.databaseobjects;

/**
 * Created by HP on 12/9/2015.
 */
public class UserInformation {
    private String userName;
    private String userAge;
    private String userGender;
    private String userRelationshipStatus;
    private byte[] userPic;

    public UserInformation(String userName, String userAge, String userGender, String userRelationshipStatus, byte[] userPic) {
        this.userName = userName;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userRelationshipStatus = userRelationshipStatus;
        this.userPic = userPic;
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
}
