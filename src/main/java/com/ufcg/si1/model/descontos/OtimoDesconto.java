package com.ufcg.si1.model.descontos;

import java.math.BigDecimal;
import com.ufcg.si1.model.Product;

public class OtimoDesconto implements Descontos{
	
	@Override
	public BigDecimal calculandoDesconto(Product produto) {
		BigDecimal preco = produto.getPrice();
		BigDecimal valor = new BigDecimal(0.25);
		BigDecimal total = preco.multiply(valor);
		return total;
	}

}
