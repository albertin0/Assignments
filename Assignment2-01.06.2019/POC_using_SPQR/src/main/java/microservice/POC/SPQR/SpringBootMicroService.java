package microservice.POC.SPQR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringBootMicroService {
    public static void main(String[] args)  {
        SpringApplication.run(SpringBootMicroService.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoggedInUserBean loggedInUserBean()    {
        return new LoggedInUserBean();
    }
}
