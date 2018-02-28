package com.ufcg.si1.model.descontos;

import java.math.BigDecimal;
import com.ufcg.si1.model.Produto;

public class SuperDesconto implements Descontos {
	
	@Override
	public BigDecimal calculandoDesconto(Produto produto) {
		BigDecimal preco = produto.getPreco();
		BigDecimal valor = new BigDecimal(0.50);
		BigDecimal total = preco.subtract(preco.multiply(valor));
		return total;
	}

}
