package com.ufcg.si1.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.ufcg.si1.model.Lot;
import org.springframework.stereotype.Service;

@Service("loteService")
public class LotServiceImpl implements LotService {

	private static final AtomicLong counter = new AtomicLong();

	private static List<Lot> lots;

	static {
		lots = new ArrayList<>();
	}

	@Override
	public Lot saveLot(Lot lote) {
		lote.setId(counter.incrementAndGet());
		lots.add(lote);

		return lote;
	}

	@Override
	public Lot findById(long id) {
		for (Lot lote : lots) {
			if (lote.getId() == id) {
				return lote;
			}
		}
		return null;
	}

	@Override
	public void updateProduct(Lot lote) {
		int index = lots.indexOf(lote);
		lots.set(index, lote);

	}

	@Override
	public void deleteLotById(long id) {
		for (Iterator<Lot> iterator = lots.iterator(); iterator.hasNext();) {
			Lot lote = iterator.next();
			if (lote.getId() == id) {
				iterator.remove();
			}
		}
	}

	@Override
	public List<Lot> findAllLots() {
		return lots;
	}

	@Override
	public int size() {
		return lots.size();
	}

	@Override
	public Iterator<Lot> getIterator() {
		return null;
	}
	
	public void getReport() {
		List<Lot> lotes = findAllLots();
		String relatorio = null;
		for (Iterator<Lot> iterator = lotes.iterator(); iterator.hasNext();) {
			Lot lote = iterator.next();
			relatorio += lote.toString() + "/n";
		}
	}
}
