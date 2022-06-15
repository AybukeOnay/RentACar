package com.kodlamaio.rentACar.core.adapters;

import java.util.HashMap;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.FindexService;

@Service
public class FindexServiceAdapter implements FindexService {

	HashMap<String, Integer> findexScores;

	@Override
	public int findexScore(String nationalty) {

		findexScores = new HashMap<String, Integer>();
		Random random = new Random();
		int findexScore = random.nextInt(1900);
		findexScores.put(nationalty, findexScore);
		System.out.println("findex score : " + findexScore);
		return findexScore;
	}

}
