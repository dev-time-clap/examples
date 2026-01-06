package de.devtime.app.dev.tool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.devtime.app.dev.tool.businesslogic.DevTimeBanner;
import de.devtime.app.dev.tool.businesslogic.MenuHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "de.devtime")
public class SpringApplicationInitializer implements CommandLineRunner {

  public static void main(String[] args) {
	  SpringApplication app = new SpringApplication(SpringApplicationInitializer.class);
	  app.setBanner(new DevTimeBanner());
	  app.run(args);
  }
  
  private final MenuHandler menuHandler;

  @Override
  public void run(String... args) {
    menuHandler.start();
  }  
}