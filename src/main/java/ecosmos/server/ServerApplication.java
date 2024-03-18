package ecosmos.server;

import com.twilio.Twilio;
import ecosmos.server.config.TwilioConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	private void setup() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuth_token());
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
