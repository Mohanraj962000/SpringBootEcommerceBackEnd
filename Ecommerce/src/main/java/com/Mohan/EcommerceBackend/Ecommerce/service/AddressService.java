package com.Mohan.EcommerceBackend.Ecommerce.service;


import com.Mohan.EcommerceBackend.Ecommerce.Payload.AddressDTO;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.CommonMapper;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.AddressRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.UserRepo;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.ResourceNotFoundException;
import com.Mohan.EcommerceBackend.Ecommerce.model.Address;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressRepo addressRepo;

    public AddressDTO createAddress(String emailId, Address address) {

        User user = userRepo.findByEmail(emailId);

        if(user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Address newAddress = new Address();
        newAddress.setBuildingName(address.getBuildingName());
        newAddress.setStreet(address.getStreet());
        newAddress.setCity(address.getCity());
        newAddress.setState(address.getState());
        newAddress.setPincode(address.getPincode());
        newAddress.setCountry(address.getCountry());
        newAddress.setUser((Set<User>) user);

        addressRepo.save(newAddress);

        return CommonMapper.INSTANCE.toAddressDTO(newAddress);
    }

    public List<AddressDTO> getAddresses() {

        List<Address> addresses = addressRepo.findAll();

        List<AddressDTO> addressDTOS = addresses.stream().map(CommonMapper.INSTANCE::toAddressDTO).toList();

        return addressDTOS;

    }

    public List<AddressDTO> getAddressesOfUser(String emailId) {
        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        List<Address> addresses = addressRepo.findByUserUserId(user.getUserId());

        return addresses.stream().map(CommonMapper.INSTANCE::toAddressDTO).toList();
    }

    public AddressDTO getAddress(String emailId, UUID addressId) {
        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Address address = addressRepo.findByAddressIdAndUserUserId(addressId,user.getUserId());

        return CommonMapper.INSTANCE.toAddressDTO(address);
    }


    public AddressDTO updateAddress(String emailId, UUID addressId, Address address) {
        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Address savedAddress = addressRepo.findByAddressIdAndUserUserId(addressId,user.getUserId());

        savedAddress.setBuildingName(address.getBuildingName());
        savedAddress.setStreet(address.getStreet());
        savedAddress.setCity(address.getCity());
        savedAddress.setState(address.getState());
        savedAddress.setPincode(address.getPincode());
        savedAddress.setCountry(address.getCountry());

        addressRepo.save(savedAddress);

        return CommonMapper.INSTANCE.toAddressDTO(savedAddress);
    }


    public String deleteAddress(UUID addressId, String emailId) {

        User user = userRepo.findByEmail(emailId);

        if (user == null){
            throw new ResourceNotFoundException("User","emailId",emailId);
        }

        Address address = addressRepo.findByAddressIdAndUserUserId(addressId,user.getUserId());

        addressRepo.delete(address);

        return "Address with " + address.getAddressId() + "deleted successfully";
    }
}
