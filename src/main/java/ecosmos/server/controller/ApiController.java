package ecosmos.server.controller;

import ecosmos.server.dto.OtpRequest;
import ecosmos.server.dto.OtpValidationRequest;
import ecosmos.server.dto.SmsMessageDto;
import ecosmos.server.service.SmsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private SmsService service;

    @PostMapping("/send-otp")
    public ResponseEntity<SmsMessageDto> smsCreator(@RequestBody OtpRequest otpRequest){
        try {
            log.info("inside send OTP :: " + otpRequest.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(service.sendSms(otpRequest));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest){
        try {
            log.info("inside Validate OTP: " + otpValidationRequest.getUsername() + " - " + otpValidationRequest.getOtpNumber());
            return ResponseEntity.status(HttpStatus.OK).body(service.validateOtp(otpValidationRequest));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
