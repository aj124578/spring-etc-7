package shop.mtcoding.springetc7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.springetc7.model.User;
import shop.mtcoding.springetc7.model.UserRepository;

@RequiredArgsConstructor
@SpringBootApplication
public class Springetc7Application {

	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository){ // 스프링 서버 실행 될때 자동 실행됨 : 더미데이터 필요할때 jpa에서는 이런방식으로 사용하면 됨
		return (args)->{
			userRepository.save(User.builder().username("ssar").password("1234").email("ssar@nate.com").role("user").build());
			userRepository.save(User.builder().username("admin").password("1234").email("admin@nate.com").role("admin").build());
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(Springetc7Application.class, args);
	}

}
