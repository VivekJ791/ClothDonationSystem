package com.example.clothdonationsystem.helper;

import com.example.clothdonationsystem.model.*;
import com.example.clothdonationsystem.model.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserHelper {
    public UserResponse userResponseFromUser(User user){
        UserResponse userResponse= new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail()!=null?user.getEmail():null);
        userResponse.setMobileNo(user.getMobileNo()!=null?user.getMobileNo():null);
        userResponse.setUsername(user.getUsername()!=null?user.getUsername():null);

        //set address
        String s = user.getAddress().getStreet() + " "+ user.getAddress().getCity();
        userResponse.setAddress(user.getAddress().getStreet()!=null || user.getAddress().getCity()!=null ?s:null);

        //set roles
        Set<Role> roles = user.getRoles();
        List<String> rolesList = roles.stream()
                .map(role -> role.getRoleName().toString())
                .collect(Collectors.toList());
        userResponse.setRoles(rolesList);

        //set donation ids
        Set<Donation> donations = user.getDonations();
        List<Long> donationsIds= donations.stream()
                        .map(donationId-> donationId.getId())
                                .collect(Collectors.toList());
        userResponse.setDonationIds(donationsIds);

        //set Payment ids
        Set<Payment> payments = user.getPayments();
        List<Long> paymentIds= payments.stream()
                .map(payment -> payment.getId())
                .collect(Collectors.toList());
        userResponse.setPaymentIds(paymentIds);

        //set feedback ids
        Set<Feedback> feedbacks = user.getFeedbacks();
        List<Long> feedbackIds= feedbacks.stream()
                .map(feedback -> feedback.getId())
                .collect(Collectors.toList());
        userResponse.setFeedbackIds(feedbackIds);

        return userResponse;
    }

    public List<UserResponse> userResponsesFromUserList(List<User> userList){
        List<UserResponse> userResponses= new ArrayList<>();
        for(User u:userList){
            userResponses.add(userResponseFromUser(u));
        }
        return userResponses;
    }
}
