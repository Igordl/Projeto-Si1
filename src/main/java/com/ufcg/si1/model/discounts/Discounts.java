package com.ufcg.si1.model.discounts;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public interface Discounts {
	
	public BigDecimal calculateDiscount(Product produto);
}
