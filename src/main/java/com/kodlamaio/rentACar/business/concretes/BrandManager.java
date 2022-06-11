package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.BrandService;
import com.kodlamaio.rentACar.business.requests.brands.CreateBrandRequest;
import com.kodlamaio.rentACar.business.requests.brands.UpdateBrandRequest;
import com.kodlamaio.rentACar.business.responses.brands.BrandResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.BrandRepository;
import com.kodlamaio.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private ModelMapperService modelMapperService;
	private BrandRepository brandRepository;

	@Autowired
	public BrandManager(BrandRepository brandRepository, ModelMapperService modelMapperService) {
		this.brandRepository = brandRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		// mapping
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class); //-->modelmapperService'den request işlemi yapar
																								//createBrand işlemi yapar,dönüş tipi olarak Brand classını verir.

		this.brandRepository.save(brand);

		return new SuccessResult("BRAND.ADDED");

	}

	@Override
	public List<BrandResponse> getAll() {
		// mapping
		List<Brand> brands = brandRepository.findAll();
		return brands.stream().map(brand -> this.modelMapperService.forResponse().map(brand, BrandResponse.class)).collect(Collectors.toList());
				//BrandResponse(b)).collect(Collectors.toList());

	}

	@Override
	public Result deleteById(int id) {
		
		brandRepository.deleteById(id);
		return new SuccessResult("BRAND.DELETED");

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest, int id) {
		// mapping
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		//brand.setName(updateBrandRequest.getName());
		Optional<Brand> currentBrand = brandRepository.findById(id);
		if (currentBrand.isPresent()) {
			Brand foundBrand = currentBrand.get();
			foundBrand.setName(brand.getName());
			brandRepository.save(foundBrand);

		}
		return new SuccessResult("BRAND.UPDATED");

	}

	@SuppressWarnings("deprecation")
	@Override
	public Brand getBrandById(int id) {
		return brandRepository.getById(id);
	}

}
