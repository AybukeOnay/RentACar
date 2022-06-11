package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.BrandResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService;
	
	public BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}

	@GetMapping("/getall")
	public List<BrandResponse> getAll()
	{
		return brandService.getAll();
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateBrandRequest createBrandRequest)
	{
		return brandService.add(createBrandRequest);
	}
	
	@DeleteMapping("/{id}")
	public Result delete(@PathVariable int id)
	{
		return brandService.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public Result update(@PathVariable int id, @RequestBody UpdateBrandRequest updateBrandRequest)
	{
		return brandService.update(updateBrandRequest, id);
	}

}
