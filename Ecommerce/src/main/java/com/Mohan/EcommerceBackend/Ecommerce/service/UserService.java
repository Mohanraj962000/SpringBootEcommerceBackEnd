package com.Mohan.EcommerceBackend.Ecommerce.service;

import com.Mohan.EcommerceBackend.Ecommerce.Payload.CommonMapper;
import com.Mohan.EcommerceBackend.Ecommerce.Payload.UserDTO;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.RoleRepo;
import com.Mohan.EcommerceBackend.Ecommerce.Repo.UserRepo;
import com.Mohan.EcommerceBackend.Ecommerce.config.AppConstants;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.APIException;
import com.Mohan.EcommerceBackend.Ecommerce.exceptions.ResourceNotFoundException;
import com.Mohan.EcommerceBackend.Ecommerce.model.Address;
import com.Mohan.EcommerceBackend.Ecommerce.model.Role;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import com.Mohan.EcommerceBackend.Ecommerce.model.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService {

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    public UserDTO registerUser(User user) {

        User savedUser = userRepo.findByEmail(user.getEmail());


        if (savedUser != null) {
            throw new APIException("User Already exists with email " + user.getEmail());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        user.setPassword(encoder.encode(user.getPassword()));

        Role role = roleRepo.findByRoleName(AppConstants.ROLE_USER);
        user.getRoles().add(role);

        User newuser = userRepo.save(user);
        return CommonMapper.INSTANCE.toUserDTO(newuser);

    }

    public UserResponse getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> pagedUser = userRepo.findAll(pageable);

        List<User> users = pagedUser.getContent();

        if (users.size() == 0) {
            throw new APIException("No User exists");
        }

        List<UserDTO> userDTOS = users.stream().map(user -> CommonMapper.INSTANCE.toUserDTO(user)).toList();

        UserResponse userResponse = new UserResponse();
        userResponse.setContent(userDTOS);
        userResponse.setPageNumber(pagedUser.getNumber());
        userResponse.setPageSize(pagedUser.getSize());
        userResponse.setTotalPages(pagedUser.getTotalPages());
        userResponse.setTotalElements(pagedUser.getTotalElements());
        userResponse.setLastPage(pagedUser.isLast());

        return userResponse;

    }

    public UserDTO getUser(HttpServletRequest request) {

        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);


        User user = userRepo.findByEmail(emailId);

        if (user == null) {
            throw new ResourceNotFoundException("User", "email", emailId);
        }

        UserDTO userDTO = CommonMapper.INSTANCE.toUserDTO(user);


        return userDTO;
    }


    public UserDTO updateUser(UserDTO dto, HttpServletRequest request) {
        String token = jwtService.extractToken(request);
        String emailId = jwtService.extractUserName(token);

        User user = userRepo.findByEmail(emailId);

        if (user == null) {
            throw new ResourceNotFoundException("User", "emailId", emailId);
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setMobileNumber(dto.getMobileNumber());

        userRepo.save(user);

        UserDTO userDTO = CommonMapper.INSTANCE.toUserDTO(user);


        return userDTO;

    }


    public String deleteUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));


        userRepo.delete(user);

        return "User Deleted Successfully";
    }
}
