package com.Mohan.EcommerceBackend.Ecommerce.service;

import com.Mohan.EcommerceBackend.Ecommerce.Repo.UserRepo;
import com.Mohan.EcommerceBackend.Ecommerce.config.UserPrincipal;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.ResourceNotFoundException;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);

        if (user == null){
            throw new ResourceNotFoundException("User","email",username);
        }

        return new UserPrincipal(user);
    }
}
