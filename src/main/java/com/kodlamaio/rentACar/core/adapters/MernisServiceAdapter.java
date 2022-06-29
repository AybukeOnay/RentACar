package com.kodlamaio.rentACar.core.adapters;

import org.springframework.stereotype.Service;

import com.kodlamaio.rentACar.business.abstracts.UserCheckService;
import com.kodlamaio.rentACar.business.requests.individuals.CreateIndividualCustomerRequest;
import com.kodlamaio.rentACar.entities.concretes.IndividualCustomer;

import tr.gov.nvi.tckimlik.WS.KPSPublicSoapProxy;

@Service
public class MernisServiceAdapter implements UserCheckService {

	@Override
	public boolean checkIfRealPerson(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		try {
			KPSPublicSoapProxy kpsPublicSoapProxy = new KPSPublicSoapProxy();

			boolean isValidUser = kpsPublicSoapProxy.TCKimlikNoDogrula(Long.parseLong(createIndividualCustomerRequest.getNationality()),
					createIndividualCustomerRequest.getFirstName().toUpperCase(), createIndividualCustomerRequest.getLastName().toUpperCase(), createIndividualCustomerRequest.getBirthDate().getYear());
			return isValidUser;
			
		} catch (Exception e) {
			System.out.println("Giriş bilgileri doğru değil");
		}
		return false;
	}

}
