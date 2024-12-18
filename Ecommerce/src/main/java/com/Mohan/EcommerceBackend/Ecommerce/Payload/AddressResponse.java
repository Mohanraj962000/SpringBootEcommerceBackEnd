package com.Mohan.EcommerceBackend.Ecommerce.Payload;

import com.Mohan.EcommerceBackend.Ecommerce.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse {

    private List<Address> content;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
    private int totalPage;
    private boolean isLastPage;
}
