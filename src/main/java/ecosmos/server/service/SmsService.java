package ecosmos.server.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import ecosmos.server.config.TwilioConfig;
import ecosmos.server.dto.OtpRequest;
import ecosmos.server.dto.OtpStatus;
import ecosmos.server.dto.OtpValidationRequest;
import ecosmos.server.dto.SmsMessageDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@AllArgsConstructor
@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;

    private final Map<String, String> otpMap = new HashMap<>();

    public SmsMessageDto sendSms(OtpRequest otpRequest) {
        SmsMessageDto smsMessageDto = null;
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getSender_number());
            String otp = generateOtp();
            String otpMessage = "OTP: " + otp;
            Message message = Message.creator(to, from, otpMessage).create();
            otpMap.put(otpRequest.getUsername(), otp);
            smsMessageDto = new SmsMessageDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            smsMessageDto = new SmsMessageDto(OtpStatus.FAILED, e.getMessage());
            e.printStackTrace();
        }

        return smsMessageDto;
    }

    public String validateOtp(OtpValidationRequest otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        String username = null;
        for (String key : keys)
            username = key;
        if (otpValidationRequest.getUsername().equals(username)) {
            otpMap.remove(username, otpValidationRequest.getOtpNumber());
            return "OTP is valid!";
        } else {
            return "OTP is invalid!";
        }
    }

    private String generateOtp() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}


