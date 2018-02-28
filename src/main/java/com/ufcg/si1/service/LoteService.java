package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.si1.model.Lot;

public interface LoteService {

	List<Lot> findAllLotes();

	Lot findById(long id);

	void updateProduto(Lot user);

	void deleteLoteById(long id);

	int size();

	Iterator<Lot> getIterator();

	Lot saveLote(Lot lote);
}
