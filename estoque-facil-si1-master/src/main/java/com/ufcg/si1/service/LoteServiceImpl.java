package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.ufcg.si1.model.Lote;
import org.springframework.stereotype.Service;

@Service("loteService")
public class LoteServiceImpl implements LoteService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Lote> lotes;

	static {
		lotes = new ArrayList<>();
	}

	@Override
	public Lote saveLote(Lote lote) {
		lote.setId(counter.incrementAndGet());
		lotes.add(lote);

		return lote;
	}

	@Override
	public Lote findById(long id) {
		for (Lote lote : lotes) {
			if (lote.getId() == id) {
				return lote;
			}
		}
		return null;
	}

	@Override
	public void updateProduto(Lote lote) {
		int index = lotes.indexOf(lote);
		lotes.set(index, lote);

	}

	@Override
	public void deleteLoteById(long id) {
		for (Iterator<Lote> iterator = lotes.iterator(); iterator.hasNext();) {
			Lote lote = iterator.next();
			if (lote.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<Lote> findAllLotes() {
		return lotes;
	}

	@Override
	public int size() {
		return lotes.size();
	}

	@Override
	public Iterator<Lote> getIterator() {
		return null;
	}
}
