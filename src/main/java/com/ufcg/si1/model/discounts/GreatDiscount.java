package com.ufcg.si1.model.discounts;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public class GreatDiscount implements Discounts{
	
	@Override
	public BigDecimal calculateDiscount(Product produto) {
		BigDecimal preco = produto.getPrice();
		BigDecimal valor = new BigDecimal(0.25);
		BigDecimal total = preco.multiply(valor);
		return total;
	}

}
