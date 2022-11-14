package bank.service.user_management.retrofit.user_management;

import com.google.gson.annotations.SerializedName;

public class UserRetrofitModel {
    @SerializedName("UserId")
    private int userId;

    @SerializedName("UserFirstName")
    private String userFirstName;

    @SerializedName("UserMiddleName")
    private String userMiddleName;

    @SerializedName("UserSurname")
    private String userSurname;

    @SerializedName("UserEmail")
    private String userEmail;

    @SerializedName("CountyName")
    private String countyName;

    @SerializedName("OrganizationName")
    private String organizationName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserMiddleName() {
        return userMiddleName;
    }

    public void setUserMiddleName(String userMiddleName) {
        this.userMiddleName = userMiddleName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
