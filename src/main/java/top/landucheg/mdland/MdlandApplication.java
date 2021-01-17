package top.landucheg.mdland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class MdlandApplication {
    public static void main(String[] args) {
        SpringApplication.run(MdlandApplication.class, args);
    }
}
