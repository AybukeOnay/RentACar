package com.kodlamaio.rentACar.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.requests.additionalItems.CreateAdditionalItemRequest;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;

@RestController
@RequestMapping("/api/additionalItems")
public class AdditionalItemsController {
	
	@Autowired
	private AdditionalItemService additionalItemService;
	
	@PostMapping("/add")
	public Result add(CreateAdditionalItemRequest createAdditionalItemRequest) {
		
		additionalItemService.add(createAdditionalItemRequest);
		return new SuccessResult();
	}
	
	

}
