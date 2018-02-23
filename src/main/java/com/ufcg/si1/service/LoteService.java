package com.ufcg.si1.service;

import java.util.Iterator;
import java.util.List;

import com.ufcg.si1.model.Lote;

public interface LoteService {

	List<Lote> findAllLotes();

	Lote findById(long id);

	void updateProduto(Lote user);

	void deleteLoteById(long id);

	int size();

	Iterator<Lote> getIterator();

	Lote saveLote(Lote lote);
}
