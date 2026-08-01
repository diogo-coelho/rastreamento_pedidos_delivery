package br.com.foody_delivery.order_tracking.domain.user.service;

import br.com.foody_delivery.order_tracking.domain.user.model.User;
import br.com.foody_delivery.order_tracking.dto.user.UserRequestDto;

public interface UserService {

    User register(UserRequestDto userRequestDto);

}
