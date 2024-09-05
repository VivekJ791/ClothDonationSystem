package com.example.clothdonationsystem.controller;

import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.request.UpdateUserRequest;
import com.example.clothdonationsystem.model.response.UserResponse;
import com.example.clothdonationsystem.service.UserService;
import com.example.clothdonationsystem.utils.PaginationRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/getUserById")
    public ResponseEntity<UserResponse> getUserById(Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<Object> getAllUsers(PaginationRequestModel paginationRequestModel){
        return ResponseEntity.ok(userService.getAllUsersList(paginationRequestModel));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<Object> getAllUsers(@RequestParam Long userId,@RequestBody UpdateUserRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userId,request));
    }

}
