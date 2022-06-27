package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemService;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.CreateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.DeleteOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.UpdateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetOrderedAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedAdditionalItems")
public class OrderedAdditionalItemsController {

	private OrderedAdditionalItemService orderedAdditionalItemService;

	public OrderedAdditionalItemsController(OrderedAdditionalItemService orderedAdditionalItemService) {
		this.orderedAdditionalItemService = orderedAdditionalItemService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateOrderedAdditionalItemRequest createOrderedAdditionalItemRequest) {
		return orderedAdditionalItemService.add(createOrderedAdditionalItemRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteOrderedAdditionalItemRequest deleteOrderedAdditionalItemRequest) {
		return orderedAdditionalItemService.delete(deleteOrderedAdditionalItemRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody UpdateOrderedAdditionalItemRequest updateOrderedAdditionalItemRequest) {
		return orderedAdditionalItemService.update(updateOrderedAdditionalItemRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetOrderedAdditionalItemResponse> getById(@RequestParam int id){
		return this.orderedAdditionalItemService.getById(id);
	}
	
	@GetMapping("/getAll")
	public DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll(){
		return this.orderedAdditionalItemService.getAll();
	}
	
}
