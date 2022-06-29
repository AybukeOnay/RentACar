package com.kodlamaio.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.AdditionalItemService;
import com.kodlamaio.rentACar.business.abstracts.InvoiceService;
import com.kodlamaio.rentACar.business.abstracts.OrderedAdditionalItemService;
import com.kodlamaio.rentACar.business.abstracts.RentalService;
import com.kodlamaio.rentACar.business.requests.invoices.CreateInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.DeleteInvoiceRequest;
import com.kodlamaio.rentACar.business.requests.invoices.UpdateInvoiceRequest;
import com.kodlamaio.rentACar.business.responses.invoices.GetAllInvoicesResponse;
import com.kodlamaio.rentACar.business.responses.invoices.GetInvoiceResponse;
import com.kodlamaio.rentACar.core.utilities.exceptions.BusinessException;
import com.kodlamaio.rentACar.core.utilities.mapping.ModelMapperService;
import com.kodlamaio.rentACar.core.utilities.results.DataResult;
import com.kodlamaio.rentACar.core.utilities.results.Result;
import com.kodlamaio.rentACar.core.utilities.results.SuccessDataResult;
import com.kodlamaio.rentACar.core.utilities.results.SuccessResult;
import com.kodlamaio.rentACar.dataAccess.abstracts.InvoiceRepository;
import com.kodlamaio.rentACar.entities.concretes.AdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Invoice;
import com.kodlamaio.rentACar.entities.concretes.OrderedAdditionalItem;
import com.kodlamaio.rentACar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService{

	private InvoiceRepository invoiceRepository;
	private ModelMapperService modelMapperService;
	private OrderedAdditionalItemService orderedAdditionalItemService;
	private RentalService rentalService;
	private AdditionalItemService additionalItemService;

	public InvoiceManager(InvoiceRepository invoiceRepository, ModelMapperService modelMapperService,
			OrderedAdditionalItemService orderedAdditionalItemService, RentalService rentalService,
			AdditionalItemService additionalItemService) {
		this.invoiceRepository = invoiceRepository;
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalItemService = orderedAdditionalItemService;
		this.rentalService = rentalService;
		this.additionalItemService = additionalItemService;
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		checkIfInvoiceNumberExists(createInvoiceRequest.getInvoiceNumber());
		checkIfInvoiceExistById(createInvoiceRequest.getId());
		checkIfRentalIdExists(createInvoiceRequest.getRentalId());
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		invoice.setCurrentDate(LocalDate.now());
		invoice.setTotalPrice(calculateTotalPrice(createInvoiceRequest.getRentalId()));
		
		this.invoiceRepository.save(invoice);
		
		return new SuccessResult("INVOICE.ADDED");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {
		checkIfInvoiceExistById(deleteInvoiceRequest.getId());
		Invoice invoice = this.invoiceRepository.findById(deleteInvoiceRequest.getId());
		invoice.setState(1); //1 --> fatura iptal edilmiş.
		this.invoiceRepository.save(invoice);
		return new SuccessResult("INVOICE.DELETED");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public DataResult<List<GetAllInvoicesResponse>> getAll() {
		
		List<Invoice> getAllInvoicesResponses = this.invoiceRepository.findAll();
		List<GetAllInvoicesResponse> response = getAllInvoicesResponses.stream()
				.map(invoice -> this.modelMapperService.forResponse()
				.map(getAllInvoicesResponses, GetAllInvoicesResponse.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<GetAllInvoicesResponse>>(response, "GET.ALL.INVOICES");
	}

	@Override
	public DataResult<GetInvoiceResponse> getById(int id) {
		checkIfInvoiceExistById(id);
		Invoice invoice = this.invoiceRepository.findById(id);
		GetInvoiceResponse response = this.modelMapperService.forResponse().map(invoice, GetInvoiceResponse.class);
		return new SuccessDataResult<GetInvoiceResponse>(response, "GET.INVOICE");
	}

	@Override
	public DataResult<List<AdditionalItem>> getAllAdditionalItems(int rentalId) {
		
		List<OrderedAdditionalItem> orderedAdditionalItemServices = this.orderedAdditionalItemService.getByRentalId(rentalId);
		List<AdditionalItem> additionalItems = new ArrayList<AdditionalItem>();
		
		for (OrderedAdditionalItem additionalService : orderedAdditionalItemServices) {
			AdditionalItem additionalItem = this.additionalItemService.getAdditionalItemById(additionalService.getAdditionalItem().getId());
			additionalItems.add(additionalItem);
		}
		return new SuccessDataResult<List<AdditionalItem>>(additionalItems);
	}	
	
	private double calculateTotalPrice(int rentalId) {
		Rental rental = this.rentalService.getRentalById(rentalId);
		double totalPrice = rental.getTotalPrice() + allRentalAdditionalTotalPrice(rentalId);
		return totalPrice;
	}
	
	private double allRentalAdditionalTotalPrice(int id) {
		double totalAdditionalService = 0;
		List<OrderedAdditionalItem> additionalServices = this.orderedAdditionalItemService.getByRentalId(id);
		for (OrderedAdditionalItem additionalService : additionalServices) {
			totalAdditionalService += additionalService.getTotalPrice();
		}
		return totalAdditionalService;
	}	
	
	private void checkIfInvoiceExistById(int id) {
		Invoice invoice = invoiceRepository.findById(id);
		if (invoice != null)
			throw new BusinessException("INVOICE.EXIST");
	}
	
//	private void checkIfInvoiceStatus(int id) {
//		Invoice invoice = this.invoiceRepository.findByRentalId(id);
//		if ((invoice != null) && (invoice.get() != 1)) {
//			throw new BusinessException("RENTAL.HAVE.ALREADY.A.INVOICE");
//		}
//	}
	
	private void checkIfInvoiceNumberExists(int invoiceNumber) {
		Invoice invoice = this.invoiceRepository.findByInvoiceNumber(invoiceNumber);
		if (invoice != null) {
			throw new BusinessException("INVOICE.NUMBER.EXISTS");
		}
	}
	
	private void checkIfRentalIdExists(int rentalId) {
		
		Rental rental = this.rentalService.getRentalById(rentalId);
		if(rental == null) {
			throw new BusinessException("THERE.IS.NO.RENTED.CAR");
		}
	}
	

}
