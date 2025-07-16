package com.louiskhanh.airbnb_clone_be;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirbnbCloneBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirbnbCloneBeApplication.class, args);


		String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "myuser";
        String password = "secret";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to PostgreSQL successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
