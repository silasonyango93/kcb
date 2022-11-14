package bank.entity.account_management;

import bank.entity.card_management.CardEntity;
import bank.entity.card_management.CardTypesEntity;
import bank.entity.user_management.User;

import javax.persistence.*;
import java.util.List;

@javax.persistence.Entity
@Table(name = "accounts")
public class AccountsEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountId")
    @JoinColumn (name="AccountId",referencedColumnName="UserId",nullable=false,unique=true)
    private int accountId;

    @Column(name = "UserId")
    private int userId;

    @Column(name = "AccountName")
    private String accountName;

    @Column(name = "AccountNumber")
    private String accountNumber;

    @Column(name = "AccountBalance")
    private double accountBalance;

    @Column(name = "IsTransactionOnGoing")
    private int isTransactionOnGoing;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userEntity;

    @OneToMany(
            mappedBy = "accountsEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CardEntity> cardEntityList;

    public AccountsEntity() {
    }

    public AccountsEntity(int userId, String accountName, String accountNumber, double accountBalance, int isTransactionOnGoing) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.isTransactionOnGoing = isTransactionOnGoing;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getIsTransactionOnGoing() {
        return isTransactionOnGoing;
    }

    public void setIsTransactionOnGoing(int isTransactionOnGoing) {
        this.isTransactionOnGoing = isTransactionOnGoing;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

    public List<CardEntity> getCardEntityList() {
        return cardEntityList;
    }

    public void setCardEntityList(List<CardEntity> cardEntityList) {
        this.cardEntityList = cardEntityList;
    }
}
