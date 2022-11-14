package bank.entity.transaction_management;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "transaction_types")
public class TransactionTypesEntity implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionTypeId")
    private int transactionTypeId;

    @Column(name = "TransactionTypeDescription")
    private String transactionTypeDescription;

    @Column(name = "TransactionTypeCode")
    private int transactionTypeCode;

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionTypeDescription() {
        return transactionTypeDescription;
    }

    public void setTransactionTypeDescription(String transactionTypeDescription) {
        this.transactionTypeDescription = transactionTypeDescription;
    }

    public int getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(int transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }
}
