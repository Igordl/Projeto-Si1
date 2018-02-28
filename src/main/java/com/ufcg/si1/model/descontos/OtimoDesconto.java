package com.ufcg.si1.model.descontos;

import java.math.BigDecimal;
import com.ufcg.si1.model.Produto;

public class OtimoDesconto implements Descontos{
	
	@Override
	public BigDecimal calculandoDesconto(Produto produto) {
		BigDecimal preco = produto.getPreco();
		BigDecimal valor = new BigDecimal(0.25);
		BigDecimal total = preco.multiply(valor);
		return total;
	}

}
