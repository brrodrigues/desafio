package rio.brunorodrigues.batchprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("rio.brunorodrigues.batchprogram.repository")
public class BatchProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchProgramApplication.class, args);
	}

//	@Bean
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().build();
//	}

//	@Bean
//	public LocalEntityManagerFactoryBean createEntityManager() {
//		LocalEntityManagerFactoryBean factoryBean = new LocalEntityManagerFactoryBean();
//		factoryBean.setPersistenceUnitName("data");
//
//		return factoryBean;
//	}

}
