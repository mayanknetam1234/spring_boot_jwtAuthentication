package com.mayank.jwtAuthentication.service.impl;

import com.mayank.jwtAuthentication.entity.User;
import com.mayank.jwtAuthentication.repository.UserRepository;
import com.mayank.jwtAuthentication.service.EmailService;
import com.mayank.jwtAuthentication.service.OtpService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final EmailService emailService;
    private final UserRepository userRepository;

    public OtpServiceImpl(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }


    @Override
    public String generateOtp() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    @Override
    public void sendOtp(String email, String otp) throws RuntimeException{
        String subject="VERIFICATION CODE";
        String verificationCode = "VERIFICATION CODE " + otp;
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";
        try {
            emailService.SendEmail(email,subject,htmlMessage);
        } catch (MessagingException e) {
            //TODO :-SEND CUSTOMIZED MESSAGE
            throw new RuntimeException(e);
        }

    }

    @Override
    public void reSendOtp(String email) throws RuntimeException{
        Optional<User> userOptional=userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            User user=userOptional.get();
            if(!user.getEnabled()){
                user.setVerificationCode(generateOtp());
                user.setVerificationExpiresAt(LocalDateTime.now().plusMinutes(10));
                userRepository.save(user);
                sendOtp(user.getEmail(),user.getVerificationCode());
            }
            else {
                throw new RuntimeException("User Already Verified");
            }

        }
        else {
            throw new RuntimeException("User not found");
        }

    }
}
