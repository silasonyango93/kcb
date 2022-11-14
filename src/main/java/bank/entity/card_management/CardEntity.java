package bank.entity.card_management;

import bank.entity.account_management.AccountsEntity;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "cards")
public class CardEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CardId")
    @JoinColumn (name="CardId",referencedColumnName="CardTypeId",nullable=false,unique=true)
    @JoinColumn (name="CardId",referencedColumnName="AccountId",nullable=false,unique=true)
    private int cardId;

    @Column(name = "CardTypeId")
    private int cardTypeId;

    @Column(name = "AccountId")
    private int accountId;

    @Column(name = "CardName")
    private String cardName;

    @Column(name = "CardNumber")
    private String cardNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private CardTypesEntity cardTypesEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    private AccountsEntity accountsEntity;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardTypesEntity getCardTypesEntity() {
        return cardTypesEntity;
    }

    public void setCardTypesEntity(CardTypesEntity cardTypesEntity) {
        this.cardTypesEntity = cardTypesEntity;
    }
}
