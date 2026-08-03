package br.com.foody_delivery.order_tracking.domain.address.service;

import br.com.foody_delivery.order_tracking.domain.address.model.Address;
import br.com.foody_delivery.order_tracking.dto.address.AddressRequestDto;

import java.util.List;

public interface AddressService {

    Address createAddressByUser(String userId, AddressRequestDto addressRequestDto);

    List<Address> listAddresses();

}
