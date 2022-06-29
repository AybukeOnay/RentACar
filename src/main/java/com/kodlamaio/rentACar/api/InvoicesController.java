package com.kodlamaio.rentACar.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.UpdateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateInvoiceRequest createInvoiceRequest) {
		return invoiceService.add(createInvoiceRequest);
	}

	@PostMapping("/delete")
	public Result delete(@RequestBody DeleteInvoiceRequest deleteInvoiceRequest) {
		return invoiceService.delete(deleteInvoiceRequest);
	}

	@PostMapping("/update")
	public Result update(@RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
		return invoiceService.update(updateInvoiceRequest);
	}

	@GetMapping("/getById")
	public DataResult<GetInvoiceResponse> getById(@RequestParam int id) {
		return this.invoiceService.getById(id);
	}

	@GetMapping("/getAll")
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		return this.invoiceService.getAll();
	}

	@GetMapping("/getAllAdditionalItems")
	public DataResult<List<AdditionalItem>> getAllAdditionalItems(@RequestParam int rentalId) {
		return this.invoiceService.getAllAdditionalItems(rentalId);
	}
}
