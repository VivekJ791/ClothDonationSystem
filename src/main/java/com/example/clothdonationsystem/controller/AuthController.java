package com.example.clothdonationsystem.controller;

import com.example.clothdonationsystem.config.jwt.JwtUtils;
import com.example.clothdonationsystem.exceptions.ResourceNotFoundException;
import com.example.clothdonationsystem.model.Address;
import com.example.clothdonationsystem.model.Role;
import com.example.clothdonationsystem.model.enums.RoleEnum;
import com.example.clothdonationsystem.model.User;
import com.example.clothdonationsystem.model.request.LoginRequest;
import com.example.clothdonationsystem.model.request.SignUpRequest;
import com.example.clothdonationsystem.model.response.JwtResponse;
import com.example.clothdonationsystem.model.response.MessageResponse;
import com.example.clothdonationsystem.repo.AddressRepository;
import com.example.clothdonationsystem.repo.RoleRepository;
import com.example.clothdonationsystem.repo.UserRepository;
import com.example.clothdonationsystem.service.AddressService;
import com.example.clothdonationsystem.service.UserService;
import com.example.clothdonationsystem.service.impl.UserDetailImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AddressService addressService;

    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailImpl userDetails = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                jwt,
                "Bearer",
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser( @RequestBody SignUpRequest signUpRequest) throws ResourceNotFoundException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        Role adminRole = roleRepository.findByRole(RoleEnum.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "ROLE_MODERATOR":
                        Role modRole = roleRepository.findByRole(RoleEnum.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByRole(RoleEnum.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);

        user.setMobileNo(signUpRequest.getMobileNo());

        //create address
        Long addressId = addressService.saveAddress(user.getId(), signUpRequest.getAddressRequest());
        Address address = addressRepository.findById(addressId).orElseThrow(()->new ResourceNotFoundException("address not present"));
        user.setAddress(address != null ? address : null);
        userRepository.save(user);
        address.setUser(user);
        addressRepository.save(address);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) throws ResourceNotFoundException, MessagingException, UnsupportedEncodingException {
        return ResponseEntity.ok(userService.generateOtp(email));
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<String> validateOtp(@RequestParam String email,@RequestParam String otp){
        return ResponseEntity.ok(userService.validateOtp(email,otp));
    }

    @PostMapping("/newPassword")
    public ResponseEntity<?> createNewPassword(@RequestParam String email,@RequestParam String password,@RequestParam String confirmPassword) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.changePassword(email,password,confirmPassword));
    }
}
