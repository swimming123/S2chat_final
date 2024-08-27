package kr.co.ict.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FinalprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalprojectApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer crosConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("■■■■■■■■■■■■■CORS TEST■■■■■■■■■■■■■");
				registry.addMapping("/**")
						.allowedOrigins(
								"http://192.168.0.39:8080",
								"http://192.168.0.4:3000",
								"http://192.168.0.39:3000",
								"http://192.168.0.43:3000",
								"http://192.168.0.45:3000",
								"http://192.168.0.34:3000",
								"http://192.168.0.67:3000",
								"http://192.168.0.82:3000",
								"http://localhost:3000",
								"http://192.168.0.56:3000",
								"http://192.168.0.27:3000",
								"http://192.168.0.58:3000",
								"http://localhost")
						.allowedHeaders("*")
						.allowedMethods("*")
						.maxAge(3600)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
				// .allowCredentials(true);
			}
		};
	}
}
