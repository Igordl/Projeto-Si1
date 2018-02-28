package com.ufcg.si1.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Lot {

    private long id;
    private Product product;
    private int amountItems;
    private String expirationDate;

    public Lot() {
        this.id = 0;
    }

    public Lot(Product product, int amountItems, String expirationDate) {
        super();
        this.product = product;
        this.amountItems = amountItems;
        this.expirationDate = expirationDate;
    }

    public Lot(long id, Product product, int amountItems, String expirationDate) {
        this.id = id;
        this.product = product;
        this.amountItems = amountItems;
        this.expirationDate = expirationDate;
    }
    
    
    //implementando a nona user story
    public void verificaValidade() throws ParseException {
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    	Date data = new Date(format.parse(expirationDate).getTime());
    	Date dataAtual = new Date(format.parse(getDateTime()).getTime());
    	if(data.after(dataAtual)) {
    		//produto vencido
    	}
    	/*else if(data.before(dataAtual)) {
    		//produto no prazo de validade
    	}*/
    }
    
    private String getDateTime() {
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = new Date();
    	return dateFormat.format(date);
    }
    

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	@Override
    public String toString() {
        return "Lote{" +
                "id=" + id +
                ", produto=" + product.getId() +
                ", numeroDeItens=" + amountItems +
                ", dataDeValidade='" + expirationDate + '\'' +
                '}';
    }
}
