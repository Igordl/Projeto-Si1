package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.ufcg.si1.model.Lot;
import org.springframework.stereotype.Service;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Lot> lotes;

	static {
		lotes = new ArrayList<>();
	}

	@Override
	public Lot saveLote(Lot lote) {
		lote.setId(counter.incrementAndGet());
		lotes.add(lote);

		return lote;
	}

	@Override
	public Lot findById(long id) {
		for (Lot lote : lotes) {
			if (lote.getId() == id) {
				return lote;
			}
		}
		return null;
	}

	@Override
	public void updateProduto(Lot lote) {
		int index = lotes.indexOf(lote);
		lotes.set(index, lote);

	}

	@Override
	public void deleteLoteById(long id) {
		for (Iterator<Lot> iterator = lotes.iterator(); iterator.hasNext();) {
			Lot lote = iterator.next();
			if (lote.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<Lot> findAllLotes() {
		return lotes;
	}

	@Override
	public int size() {
		return lotes.size();
	}

	@Override
	public Iterator<Lot> getIterator() {
		return null;
	}
	
	public void geraRelatorio() {
		List<Lot> lotes = findAllLotes();
		String relatorio = null;
		for (Iterator<Lot> iterator = lotes.iterator(); iterator.hasNext();) {
			Lot lote = iterator.next();
			relatorio += lote.toString() + "/n";
		}
	}
}
