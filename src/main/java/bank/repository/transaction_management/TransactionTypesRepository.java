package bank.repository.transaction_management;

import bank.entity.account_management.AccountsEntity;
import bank.entity.transaction_management.TransactionTypesEntity;
import bank.entity.user_management.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionTypesRepository extends JpaRepository<TransactionTypesEntity, Long> {
    public TransactionTypesEntity findByTransactionTypeCode(@Param("TransactionTypeCode") int transactionTypeCode);
}
