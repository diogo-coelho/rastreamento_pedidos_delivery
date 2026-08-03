package br.com.foody_delivery.order_tracking.domain.address.service;

import br.com.foody_delivery.order_tracking.domain.address.model.Address;
import br.com.foody_delivery.order_tracking.domain.address.repository.AddressRepository;
import br.com.foody_delivery.order_tracking.domain.user.repository.UserRepository;
import br.com.foody_delivery.order_tracking.dto.address.AddressRequestDto;
import br.com.foody_delivery.order_tracking.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Address createAddressByUser(String userId, AddressRequestDto addressRequestDto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        var address = new Address(
                user,
                addressRequestDto.street(),
                addressRequestDto.number(),
                addressRequestDto.postalCode(),
                addressRequestDto.city(),
                addressRequestDto.state(),
                addressRequestDto.country()
        );

        return addressRepository.save(address);
    }

    @Override
    public List<Address> listAddresses() {
        return addressRepository.findAll();
    }
}
