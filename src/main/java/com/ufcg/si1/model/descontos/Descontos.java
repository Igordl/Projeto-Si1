package com.ufcg.si1.model.descontos;

import java.math.BigDecimal;
import com.ufcg.si1.model.Produto;

public interface Descontos {
	
	public BigDecimal calculandoDesconto(Produto produto);
}
