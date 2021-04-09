package nl.jvandillen.slackbotateteen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SlackBotAteteenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SlackBotAteteenApplication.class, args);
	}

}
