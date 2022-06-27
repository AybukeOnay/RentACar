package com.kodlamaio.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemRepository;
import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemService;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.CreateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.DeleteOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.UpdateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetOrderedAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.AdditionalItemRepository;
import com.kodlamaio.rentACar.dataAccess.abstracts.RentalRepository;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class OrderedAdditionalItemManager implements OrderedAdditionalItemService {

	private OrderedAdditionalItemRepository orderedAdditionalItemRepository;
	private AdditionalItemRepository additionalItemRepository;
	private RentalRepository rentalRepository;
	private ModelMapperService modelMapperService;

	public OrderedAdditionalItemManager(OrderedAdditionalItemRepository orderedAdditionalItemRepository,
			AdditionalItemRepository additionalItemRepository, RentalRepository rentalRepository,
			ModelMapperService modelMapperService) {
		this.orderedAdditionalItemRepository = orderedAdditionalItemRepository;
		this.additionalItemRepository = additionalItemRepository;
		this.rentalRepository = rentalRepository;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateOrderedAdditionalItemRequest createOrderedAdditionalItemRequest) {

		OrderedAdditionalItem orderedAdditionalItems = this.modelMapperService.forRequest()
				.map(createOrderedAdditionalItemRequest, OrderedAdditionalItem.class);

		int rentalTotalDays = this.rentalRepository.findById(createOrderedAdditionalItemRequest.getRentalId()).get()
				.getTotalDays();
		orderedAdditionalItems.setTotalDays(rentalTotalDays);

		double additionalItemPrice = this.additionalItemRepository
				.findById(createOrderedAdditionalItemRequest.getAdditionalItemId()).get().getPrice();
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

		Rental rental = this.rentalRepository.findById(updateOrderedAdditionalItemRequest.getRentalId()).get();
		int rentalTotalDays = rental.getTotalDays();
		orderedAdditionalItems.setTotalDays(rentalTotalDays);

		double additionalItemPrice = this.additionalItemRepository
				.findById(updateOrderedAdditionalItemRequest.getAdditionalItemId()).get().getPrice();
		double totalPrice = calculateTotalPriceAdditionalService(rentalTotalDays, additionalItemPrice);
		orderedAdditionalItems.setTotalPrice(totalPrice);

		this.orderedAdditionalItemRepository.save(orderedAdditionalItems);
		return new SuccessResult("ADDITIONALSERVICE.UPDATED");
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
		
		OrderedAdditionalItem orderedAdditionalItem = this.orderedAdditionalItemRepository.findById(id).get();

		GetOrderedAdditionalItemResponse response = this.modelMapperService.forResponse().map(orderedAdditionalItem,
				GetOrderedAdditionalItemResponse.class);
		return new SuccessDataResult<GetOrderedAdditionalItemResponse>(response, "GET.ORDERED.ADDITIONAL.ITEM");
	}

	private double calculateTotalPriceAdditionalService(int days, double price) {
		return days * price;
	}

}
