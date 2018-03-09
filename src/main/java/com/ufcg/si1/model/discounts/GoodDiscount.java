package com.ufcg.si1.model.discounts;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public class GoodDiscount implements Discounts{

	@Override
	public BigDecimal calculateDiscount(Product product) {
		BigDecimal price = product.getPrice();
		BigDecimal value = new BigDecimal(0.10);
		BigDecimal total = price.subtract(price.multiply(value));
		return total;
	}

}
