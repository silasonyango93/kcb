package bank.repository.account_management;

import bank.entity.account_management.AccountsEntity;
import bank.entity.user_management.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {
    public AccountsEntity findByAccountNumber(@Param("AccountNumber") String accountNumber);
}
