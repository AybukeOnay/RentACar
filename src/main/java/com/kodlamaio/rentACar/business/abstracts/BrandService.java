package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.DeleteBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.GetAllBrandsResponses;
import com.kodlamaio.rentACar.business.responses.brands.GetBrandResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.Brand;

//response request pattern
public interface BrandService {

	Result add(CreateBrandRequest createBrandRequest);
	Result delete(DeleteBrandRequest deleteBrandRequest);
	Result update(UpdateBrandRequest updateBrandRequest);
	DataResult<List<GetAllBrandsResponses>> getAll();
	DataResult<GetBrandResponse> getById(int id);
	Brand getBrandById(int id);
}
