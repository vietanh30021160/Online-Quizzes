package com.swp.online_quizz.Controller.ForgotPasswordPage;

import com.swp.online_quizz.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ForgotPasswordController {
    private final UserService userService;
    @PutMapping("/verify-account")
    public ResponseEntity<String> verifyAccount(@RequestParam String email, @RequestParam String otp){
        return new ResponseEntity<>(userService.verifyAccount(email, otp), HttpStatus.OK);
    }

    @PutMapping("regenerate-otp")
    public ResponseEntity<String> regenerateOtp(@RequestParam String email){
        return new ResponseEntity<>(userService.regenerateOtp(email), HttpStatus.OK);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }
}
