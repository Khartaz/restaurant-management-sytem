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

//@Configuration
//public class DatabaseConfig {
//
//    private String dbName = System.getProperty("RDS_DB_NAME");
//    private String userName = System.getProperty("RDS_USERNAME");
//    private String password = System.getProperty("RDS_PASSWORD");
//    private String hostname = System.getProperty("RDS_HOSTNAME");
//    private String port = System.getProperty("RDS_PORT");
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);
//
//    @Bean
//    private static Connection getRemoteConnection() {
//        if (System.getenv("RDS_HOSTNAME") != null) {
//            try {
//                Class.forName("org.mysql.Driver");
//                String dbName = "ebdb";
//                String userName = "restaurant";
//                String password = "restaurant";
//                String hostname = "aa1uxsttl7z33a.cblujp7mksrf.eu-west-1.rds.amazonaws.com";
//                String port = "3306";
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
//
////    @Bean
////    public DataSource dataSource() {
////        DriverManagerDataSource dataSource = new DriverManagerDataSource();
////
////        String last = "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false";
////        String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + last;
////
////        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
////        dataSource.setUsername(userName);
////        dataSource.setPassword(password);
////        dataSource.setUrl(jdbcUrl);
////        return dataSource;
////    }
//
//}
