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
	public Lot saveLot(Lot lot) {
		lot.setId(counter.incrementAndGet());
		lots.add(lot);

		return lot;
	}

	@Override
	public Lot findById(long id) {
		for (Lot lot : lots) {
			if (lot.getId() == id) {
				return lot;
			}
		}
		return null;
	}

	@Override
	public void updateProduct(Lot lot) {
		int index = lots.indexOf(lot);
		lots.set(index, lot);

	}

	@Override
	public void deleteLotById(long id) {
		for (Iterator<Lot> iterator = lots.iterator(); iterator.hasNext();) {
			Lot lot = iterator.next();
			if (lot.getId() == id) {
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
		List<Lot> lots = findAllLots();
		String report = null;
		for (Iterator<Lot> iterator = lots.iterator(); iterator.hasNext();) {
			Lot lot = iterator.next();
			report += lot.toString() + "/n";
		}
	}
}
