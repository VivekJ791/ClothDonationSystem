package com.example.clothdonationsystem.service;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.UpdateUserRequest;
import com.example.clothdonationsystem.model.response.UserResponse;
import com.example.clothdonationsystem.utils.PaginationRequestModel;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;


public interface UserService {
    public UserResponse getUserById(Long id) throws ResourceNotFoundException;

    public Object getAllUsersList(PaginationRequestModel paginationRequestModel);

    public Long updateUser(Long id, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException;

    public String generateOtp(String email) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException;
    public String validateOtp(String email, String otp);

    public String changePassword(String email, String password, String confirmPassword) throws ResourceNotFoundException;
}
