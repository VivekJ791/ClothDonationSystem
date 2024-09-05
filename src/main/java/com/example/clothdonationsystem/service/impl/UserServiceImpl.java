package com.example.clothdonationsystem.service.impl;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.helper.UserHelper;
import com.example.clothdonationsystem.model.Address;
import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.model.request.UpdateUserRequest;
import com.example.clothdonationsystem.model.response.UserResponse;
import com.example.clothdonationsystem.repo.AddressRepository;
import com.example.clothdonationsystem.repo.UserRepository;
import com.example.clothdonationsystem.service.AddressService;
import com.example.clothdonationsystem.service.EmailService;
import com.example.clothdonationsystem.service.OTPService;
import com.example.clothdonationsystem.service.UserService;
import com.example.clothdonationsystem.utils.PaginationRequestModel;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserHelper userHelper;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OTPService otpService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmailService emailService;


    @Override
    public UserResponse getUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("no user exist for give id"));
        return userHelper.userResponseFromUser(user);
    }

    @Override
    public Object getAllUsersList(PaginationRequestModel paginationRequestModel) {
        List<User> usersList = userRepository.findAll();
        return userHelper.userResponsesFromUserList(usersList);
    }

    @Override
    public Long updateUser(Long id, UpdateUserRequest request) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user for the given id"));
        user.setEmail(request.getEmail()!=null?request.getEmail():null);
        if (userRepository.existsByUsername(request.getUserName())){
            log.error("sorry this username is taken");
        }else{
            user.setUsername(request.getUserName()!=null?request.getUserName():null);
        }

        Long addressId = addressService.saveAddress(request.getAddressRequest());
        Address address = addressRepository.findById(addressId).get();
        user.setAddress(address);
        addressRepository.save(address);
            userRepository.save(user);
        return user.getId();
    }

    @Override
    public String generateOtp(String email) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        if(userRepository.existsByEmail(email)){
            String otp = otpService.generateOTP(email);
            emailService.sendOTPEmail(email, otp);
            return "otp sent to your email address";
        }else{
            throw new ResourceNotFoundException("this email id is not registered with us");
        }
    }

    private Boolean validated=Boolean.FALSE;
    @Override
    public String validateOtp(String email, String otp){
        boolean isValid = otpService.validateOTP(email, otp);
        if (isValid) {
            validated=Boolean.TRUE;
            return "OTP is valid!";
        } else {
            return "Invalid OTP or OTP expired!";
        }
    }

    @Override
    public String changePassword(String email, String password, String confirmPassword) throws ResourceNotFoundException {
        if (userRepository.existsByEmail(email)) {
            if (password != null && confirmPassword != null && password.equals(confirmPassword) && validated) {
                User user = userRepository.findUserByEmail(email);
                user.setPassword(encoder.encode(password));
                userRepository.save(user);
                return "Password changed successfully";
            } else {
                log.info("Passwords do not match or OTP isn't validated");
                return "Passwords do not match or OTP validation failed";
            }
        } else {
            throw new ResourceNotFoundException("No such email ID exists");
        }
    }

}
