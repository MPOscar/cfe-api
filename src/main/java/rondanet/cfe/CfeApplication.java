package rondanet.cfe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
@EnableCaching
public class CfeApplication  {

	public static void main(String[] args) {
		SpringApplication.run(CfeApplication.class, args);
	}

}
