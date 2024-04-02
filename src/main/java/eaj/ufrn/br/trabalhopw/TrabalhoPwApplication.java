package eaj.ufrn.br.trabalhopw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TrabalhoPwApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrabalhoPwApplication.class, args);
	}

}
