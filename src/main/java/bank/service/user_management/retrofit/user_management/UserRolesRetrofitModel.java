package bank.service.user_management.retrofit.user_management;

import com.google.gson.annotations.SerializedName;

public class UserRolesRetrofitModel {
    @SerializedName("UserId")
    private int userId;

    @SerializedName("UserRoleId")
    private int userRoleId;

    @SerializedName("RoleId")
    private int roleId;

    @SerializedName("ConfirmationStatus")
    private int confirmationStatus;

    @SerializedName("RoleDescription")
    private String roleDescription;

    @SerializedName("RoleCode")
    private int roleCode;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getConfirmationStatus() {
        return confirmationStatus;
    }

    public void setConfirmationStatus(int confirmationStatus) {
        this.confirmationStatus = confirmationStatus;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }
}
