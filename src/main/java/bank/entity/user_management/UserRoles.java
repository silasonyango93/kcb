package bank.entity.user_management;

import javax.persistence.*;
@javax.persistence.Entity
@Table(name = "user_roles")
public class UserRoles implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserRoleId")
    private int userRoleId;

    @Column(name = "UserId")
    private int userId;

    @Column(name = "RoleId")
    private int roleId;

    @Column(name = "ConfirmationStatus")
    private int confirmationStatus;

    public UserRoles() {
    }

    public UserRoles(int userId, int roleId, int confirmationStatus) {
        this.userId = userId;
        this.roleId = roleId;
        this.confirmationStatus = confirmationStatus;
    }

    public int getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        this.userRoleId = userRoleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
