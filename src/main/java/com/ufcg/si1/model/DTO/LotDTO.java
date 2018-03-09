package com.ufcg.si1.model.DTO;

public class LotDTO {

    private int amountItems;
    private String expirationDate;

    public LotDTO() {
    }

    public LotDTO(int numeroDeItens, String dataDeValidade) {
        super();
        this.amountItems = numeroDeItens;
        this.expirationDate = dataDeValidade;
    }

    public int getAmountItems() {
        return amountItems;
    }

    public void setAmountItems(int numeroDeItens) {
        this.amountItems = numeroDeItens;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String dataDeValidade) {
        this.expirationDate = dataDeValidade;
    }
}
