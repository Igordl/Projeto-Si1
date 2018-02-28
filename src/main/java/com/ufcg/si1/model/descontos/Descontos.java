package com.ufcg.si1.model.descontos;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public interface Descontos {
	
	public BigDecimal calculandoDesconto(Product produto);
}
