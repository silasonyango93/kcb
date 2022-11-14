package bank.dto.user_management;

public class SimplifiedUserRolesDto {
    private String roleDescription;
    private boolean hasThisRole;

    public SimplifiedUserRolesDto(String roleDescription, boolean hasThisRole) {
        this.roleDescription = roleDescription;
        this.hasThisRole = hasThisRole;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public boolean isHasThisRole() {
        return hasThisRole;
    }

    public void setHasThisRole(boolean hasThisRole) {
        this.hasThisRole = hasThisRole;
    }
}
