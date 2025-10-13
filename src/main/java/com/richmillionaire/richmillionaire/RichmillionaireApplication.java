package com.richmillionaire.richmillionaire;

import java.net.InetAddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.richmillionaire.richmillionaire.dao")
@EntityScan(basePackages = "com.richmillionaire.richmillionaire.entity")
public class RichmillionaireApplication {

    public static void main(String[] args) {
        SpringApplication.run(RichmillionaireApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logServerInfo() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = System.getProperty("server.port", "8080");
            System.out.println(" le serveur tourne sur le port  " + port + " à l'adresse " + ip + ": " + "http://" + ip + ":" + port);
        } catch (java.net.UnknownHostException | SecurityException e) {
			System.out.println(e.getMessage());
            System.out.println("Impossible de récupérer l'adresse IP du serveur. Assurez-vous que le serveur est configuré correctement.");
        }
    }
}
