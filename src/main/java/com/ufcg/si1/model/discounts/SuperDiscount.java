package com.ufcg.si1.model.discounts;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public class SuperDiscount implements Discounts {
	
	@Override
	public BigDecimal calculateDiscount(Product produto) {
		BigDecimal preco = produto.getPrice();
		BigDecimal valor = new BigDecimal(0.50);
		BigDecimal total = preco.subtract(preco.multiply(valor));
		return total;
	}

}
