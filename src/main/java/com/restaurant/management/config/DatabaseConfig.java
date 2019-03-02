package com.restaurant.management.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DatabaseConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("restaurant");
//        dataSource.setPassword("restaurant");
//        dataSource.setUrl(
//                "jdbc:mysql://aa1uxsttl7z33a.cblujp7mksrf.eu-west-1.rds.amazonaws.com:3306/restaurant?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false");
//
//        return dataSource;
//    }

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUsername("restaurant");
//        dataSource.setPassword("restaurant");
//        dataSource.setUrl(
//                "jdbc:mysql://restaurant.cblujp7mksrf.eu-west-1.rds.amazonaws.com:3306/restaurant?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false");
//
//        return dataSource;
//    }

//    @Bean
//    private static Connection getRemoteConnection() {
//
//        if (System.getProperty("aa1uxsttl7z33a.cblujp7mksrf.eu-west-1.rds.amazonaws.com") != null) {
//            try {
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                String dbName = System.getProperty("aa1uxsttl7z33a");
//                String userName = System.getProperty("restaurant");
//                String password = System.getProperty("restaurant");
//                String hostname = System.getProperty("aa1uxsttl7z33a.cblujp7mksrf.eu-west-1.rds.amazonaws.com");
//                String port = System.getProperty("RDS_PORT");
//                String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
//                LOGGER.trace("Getting remote connection with connection string from environment variables.");
//                Connection con = DriverManager.getConnection(jdbcUrl);
//                LOGGER.info("Remote connection successful.");
//                return con;
//            }
//            catch (ClassNotFoundException e) { LOGGER.warn(e.toString());}
//            catch (SQLException e) { LOGGER.warn(e.toString());}
//        }
//        return null;
//    }
//    jdbc:mysql://aa1uxsttl7z33a.cblujp7mksrf.eu-west-1.rds.amazonaws.com:3306/aa1uxsttl7z33a?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
}
