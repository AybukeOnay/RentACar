package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.CreateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.DeleteOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.UpdateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetOrderedAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.OrderedAdditionalItemRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class OrderedAdditionalItemManager implements OrderedAdditionalItemService {

	private OrderedAdditionalItemRepository orderedAdditionalItemRepository;
	private AdditionalItemService additionalItemService;
	private RentalService rentalService;
	private ModelMapperService modelMapperService;

	public OrderedAdditionalItemManager(OrderedAdditionalItemRepository orderedAdditionalItemRepository,
			AdditionalItemService additionalItemService, RentalService rentalService,
			ModelMapperService modelMapperService) {
		this.orderedAdditionalItemRepository = orderedAdditionalItemRepository;
		this.additionalItemService = additionalItemService;
		this.rentalService = rentalService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateOrderedAdditionalItemRequest createOrderedAdditionalItemRequest) {

		OrderedAdditionalItem orderedAdditionalItems = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalItemRequest, OrderedAdditionalItem.class);

		int rentalTotalDays = this.rentalService.getRentalById(createOrderedAdditionalItemRequest.getRentalId())
				.getTotalDays();
		orderedAdditionalItems.setTotalDays(rentalTotalDays);

		double additionalItemPrice = this.additionalItemService
				.getAdditionalItemById(createOrderedAdditionalItemRequest.getAdditionalItemId()).getPrice();
		double totalPrice = calculateTotalPriceAdditionalService(rentalTotalDays, additionalItemPrice);
		orderedAdditionalItems.setTotalPrice(totalPrice);

		this.orderedAdditionalItemRepository.save(orderedAdditionalItems);
		return new SuccessResult("ORDERED.ADDITIONAL.ITEM.ADDED");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalItemRequest deleteOrderedAdditionalItemRequest) {

		this.orderedAdditionalItemRepository.deleteById(deleteOrderedAdditionalItemRequest.getId());
		return new SuccessResult("ORDERED.ADDITIONAL.ITEM.DELETED");
	}

	@Override
	public Result update(UpdateOrderedAdditionalItemRequest updateOrderedAdditionalItemRequest) {

		OrderedAdditionalItem orderedAdditionalItems = this.modelMapperService.forRequest()
				.map(updateOrderedAdditionalItemRequest, OrderedAdditionalItem.class);

		Rental rental = this.rentalService.getRentalById(updateOrderedAdditionalItemRequest.getRentalId());
		int rentalTotalDays = rental.getTotalDays();
		orderedAdditionalItems.setTotalDays(rentalTotalDays);

		double additionalItemPrice = this.additionalItemService
				.getAdditionalItemById(updateOrderedAdditionalItemRequest.getAdditionalItemId()).getPrice();
		double totalPrice = calculateTotalPriceAdditionalService(rentalTotalDays, additionalItemPrice);
		orderedAdditionalItems.setTotalPrice(totalPrice);

		this.orderedAdditionalItemRepository.save(orderedAdditionalItems);
		return new SuccessResult("ADDITIONALSERVICE.UPDATED");
	}
	
	@Override
	public OrderedAdditionalItem getOrderedAdditionalItemById(int id) {
		checkIfAdditionalItemIdExists(id);
		OrderedAdditionalItem orderedAdditionalItem = this.orderedAdditionalItemRepository.findById(id);
		return orderedAdditionalItem;
	}
	
	@Override
	public List<OrderedAdditionalItem> getByRentalId(int rentalId) {
		checkIfRentalIdExists(rentalId);
		List<OrderedAdditionalItem> orderedAdditionalItems = this.getByRentalId(rentalId);
		return orderedAdditionalItems;
	}

	@Override
	public DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll() {

		List<OrderedAdditionalItem> getAllOrderedAdditionalItems = this.orderedAdditionalItemRepository.findAll();

		List<GetAllOrderedAdditionalItemsResponse> response = getAllOrderedAdditionalItems.stream()
				.map(getAllOrderedAdditionalItem -> this.modelMapperService.forResponse()
						.map(getAllOrderedAdditionalItem, GetAllOrderedAdditionalItemsResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllOrderedAdditionalItemsResponse>>(response,
				"GET.ALL.ORDERED.ADDITIONAL.ITEM");
	}

	@Override
	public DataResult<GetOrderedAdditionalItemResponse> getById(int id) {
		
		OrderedAdditionalItem orderedAdditionalItem = this.orderedAdditionalItemRepository.findById(id);

		GetOrderedAdditionalItemResponse response = this.modelMapperService.forResponse().map(orderedAdditionalItem,
				GetOrderedAdditionalItemResponse.class);
		return new SuccessDataResult<GetOrderedAdditionalItemResponse>(response, "GET.ORDERED.ADDITIONAL.ITEM");
	}

	private double calculateTotalPriceAdditionalService(int days, double price) {
		return days * price;
	}
	
	private void checkIfRentalIdExists(int rentalId) {
		Rental rental = this.rentalService.getRentalById(rentalId);
		if(rental == null) {
			throw new BusinessException("THERE.IS.NOT.RENTAL"); 
		}
	}
	
	private void checkIfAdditionalItemIdExists(int additionalItemId) {
		AdditionalItem additionalItem = this.additionalItemService.getAdditionalItemById(additionalItemId);
		if(additionalItem == null) {
			throw new BusinessException("THERE.IS.NOT.ADDITIONAL.ITEM"); 

		}
	}

}
