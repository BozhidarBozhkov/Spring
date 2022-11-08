package modelMapper.services;

import modelMapper.entities.Address;
import modelMapper.entities.dto.AddressDTO;

public interface AddressService {
    Address create(AddressDTO data);
}
