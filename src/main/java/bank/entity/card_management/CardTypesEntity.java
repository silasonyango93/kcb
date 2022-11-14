package bank.entity.card_management;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@javax.persistence.Entity
@Table(name = "card_types")
public class CardTypesEntity implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CardTypeId")
    private int cardTypeId;

    @Column(name = "CardTypeName")
    private String cardTypeName;

    @Column(name = "CardTypeCode")
    private String cardTypeCode;

    @OneToMany(
            mappedBy = "cardTypesEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CardEntity> cardEntityList;

    public int getCardTypeId() {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId) {
        this.cardTypeId = cardTypeId;
    }

    public String getCardTypeName() {
        return cardTypeName;
    }

    public void setCardTypeName(String cardTypeName) {
        this.cardTypeName = cardTypeName;
    }

    public String getCardTypeCode() {
        return cardTypeCode;
    }

    public void setCardTypeCode(String cardTypeCode) {
        this.cardTypeCode = cardTypeCode;
    }

    public List<CardEntity> getCardEntityList() {
        return cardEntityList;
    }

    public void setCardEntityList(List<CardEntity> cardEntityList) {
        this.cardEntityList = cardEntityList;
    }
}
