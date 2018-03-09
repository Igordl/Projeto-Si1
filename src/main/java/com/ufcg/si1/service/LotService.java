package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.si1.model.Lot;

public interface LotService {

	List<Lot> findAllLots();

	Lot findById(long id);

	void updateProduct(Lot user);

	void deleteLotById(long id);

	int size();

	Iterator<Lot> getIterator();

	Lot saveLot(Lot lote);
}
