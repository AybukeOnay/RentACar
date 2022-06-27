package com.kodlamaio.rentACar.business.abstracts;

import java.util.List;

import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.CreateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.DeleteOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.requests.orderedAdditionalItem.UpdateOrderedAdditionalItemRequest;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetAllOrderedAdditionalItemsResponse;
import com.kodlamaio.rentACar.business.responses.orderedAdditionalItems.GetOrderedAdditionalItemResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalItemService {

	Result add(CreateOrderedAdditionalItemRequest createOrderedAdditionalItemRequest);
	Result delete(DeleteOrderedAdditionalItemRequest deleteOrderedAdditionalItemRequest);
	Result update(UpdateOrderedAdditionalItemRequest updateOrderedAdditionalItemRequest);
	DataResult<List<GetAllOrderedAdditionalItemsResponse>> getAll();
	DataResult<GetOrderedAdditionalItemResponse> getById(int id);
}
