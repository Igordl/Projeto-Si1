package com.ufcg.si1.model.situation;

import com.ufcg.si1.service.ProductServiceImpl;

public class Available implements Situation{
	
	private ProductServiceImpl product;
	
	public boolean containsProduct(){
		if(product.size() != 0){
			return true;
		}
		return false;
	}
}

