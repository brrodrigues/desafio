package rio.brunorodrigues.batchprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableJpaRepositories("rio.brunorodrigues.batchprogram.repository")
@EnableScheduling
public class BatchProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchProgramApplication.class, args);
    }

}
