package com.Mohan.EcommerceBackend.Ecommerce.controller;

import com.Mohan.EcommerceBackend.Ecommerce.Payload.AddressDTO;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.UserRepo;
import com.Mohan.EcommerceBackend.Ecommerce.model.Address;
import com.Mohan.EcommerceBackend.Ecommerce.service.AddressService;
import com.Mohan.EcommerceBackend.Ecommerce.service.JWTService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-Commerce")
public class AddressController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressService addressService;

    @Autowired
    private JWTService jwtService;

    @PostMapping("/public/address")
    public ResponseEntity<AddressDTO> createAddress(HttpServletRequest request, @Valid @RequestBody Address address){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        AddressDTO addressDTO = addressService.createAddress(emailId,address);

        return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/admin/address")
    public ResponseEntity<List<AddressDTO>> getAddresses(){

        List<AddressDTO> addressDTOS = addressService.getAddresses();

        return new ResponseEntity<List<AddressDTO>>(addressDTOS,HttpStatus.OK);
    }

    @GetMapping("/public/address")
    public ResponseEntity<List<AddressDTO>> getAddressesOfUser(HttpServletRequest request){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        List<AddressDTO> addressDTOS = addressService.getAddressesOfUser(emailId);

        return new ResponseEntity<List<AddressDTO>>(addressDTOS,HttpStatus.OK);
    }

    @GetMapping("/public/address/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable("addressId") UUID addressId,HttpServletRequest request){

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        AddressDTO addressDTO = addressService.getAddress(emailId,addressId);

        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.OK);

    }

    @PutMapping("/public/address/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable("addressId") UUID addressId, @Valid @RequestBody Address address, HttpServletRequest request){
        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        AddressDTO addressDTO = addressService.updateAddress(emailId,addressId,address);

        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.OK);
    }

    @DeleteMapping("/public/address/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable("addressId") UUID addressId,HttpServletRequest request){
        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        String response = addressService.deleteAddress(addressId,emailId);

        return new ResponseEntity<String>(response,HttpStatus.OK);

    }
}
