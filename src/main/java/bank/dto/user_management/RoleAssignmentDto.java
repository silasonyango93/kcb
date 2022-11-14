package bank.dto.user_management;

public class RoleAssignmentDto {
    private int roleId;
    private boolean toBeAssignedThisRole;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isToBeAssignedThisRole() {
        return toBeAssignedThisRole;
    }

    public void setToBeAssignedThisRole(boolean toBeAssignedThisRole) {
        this.toBeAssignedThisRole = toBeAssignedThisRole;
    }
}
