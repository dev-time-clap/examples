package de.devtime.app.example;

import java.util.Arrays;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;

/**
 * Main entry point on the application server for this component.
 * 
 * @author Morrigan
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "de.devtime")
public class SpringApplicationInitializer {

  /**
   * Program entry point.
   * 
   * @param args not used
   * @since 1.0.0
   */
  public static void main(String[] args) {
    log.info("Start application with parameters: {}", Arrays.asList(args));
    SpringApplication.run(SpringApplicationInitializer.class, args);
  }
  
  @Bean
  public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
    // FIXME Make context path configurable via properties 
    // by Morrigan on 17.11.2025
    return factory -> factory.setContextPath("/example");
  }
  
  @Bean(initMethod = "start", destroyMethod = "stop")
  public Server inMemoryH2DatabaseServer() throws Exception {
    return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
  }
}