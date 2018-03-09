package com.ufcg.si1.model.DTO;

public class LotDTO {

    private int amountItems;
    private String expirationDate;

    public LotDTO() {
    }

    public LotDTO(int amountItems, String expirationDate) {
        super();
        this.amountItems = amountItems;
        this.expirationDate = expirationDate;
    }

    public int getAmountItems() {
        return amountItems;
    }

    public void setAmountItems(int amountItems) {
        this.amountItems = amountItems;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
