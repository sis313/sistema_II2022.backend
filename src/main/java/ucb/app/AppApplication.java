package ucb.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AppApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("https://serviceprojectspring.herokuapp.com",
						"http://localhost:4200", "http://localhost:9000", "https://shell-app-sand.vercel.app",
						"https://shell-spa-beta.vercel.app")
						.allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}
}
