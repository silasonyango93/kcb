package bank.repository.user_management;

import bank.entity.user_management.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {
    public List<UserRoles> findByUserId(@Param("UserId") int userId);

    public UserRoles findByUserRoleId(@Param("UserRoleId") int userRoleId);
}
