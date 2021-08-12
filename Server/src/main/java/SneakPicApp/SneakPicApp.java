package SneakPicApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages ={"Controllers", "SecurityConfiguration"})
public class SneakPicApp {
    public static void main(String[] args) {
        //User aba = new User("k@mail.com","pass", false);
        SpringApplication.run(SneakPicApp.class, args);
    }

}

