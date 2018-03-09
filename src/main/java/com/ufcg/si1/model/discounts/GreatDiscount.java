package com.ufcg.si1.model.discounts;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public class GreatDiscount implements Discounts{
	
	@Override
	public BigDecimal calculateDiscount(Product product) {
		BigDecimal price = product.getPrice();
		BigDecimal value = new BigDecimal(0.25);
		BigDecimal total = price.multiply(value);
		return total;
	}

}
