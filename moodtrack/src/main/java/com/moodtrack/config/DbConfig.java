package com.moodtrack.config;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class DbConfig {
    @Bean
    public static Connection getRDSConnection() {
        if (System.getenv("RDS_HOSTNAME") != null) {
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                String dbName = System.getenv("RDS_DB_NAME");
                String userName = System.getenv("RDS_USERNAME");
                String password = System.getenv("RDS_PASSWORD");
                String hostname = System.getenv("RDS_HOSTNAME");
                String port = System.getenv("RDS_PORT");
                String jdbcUrl = "jdbc:oracle:thin://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
                Connection con = DriverManager.getConnection(jdbcUrl);
                return con;
            }
            catch (ClassNotFoundException e) { }
            catch (SQLException e) {}
        }
        return null;
    }

    private Gson gson = new Gson();

    /**
     * Customize the data source config values reading from Bean class
     * Instead of reading from application.yaml
     */
    @Bean
    public DataSource dataSource() {
        SecretValue secretValue = getSecretValue();
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .password(secretValue.getPassword())
                .username(secretValue.getUsername())
                .url("jdbc:" + secretValue.getEngine() + "://" + secretValue.getHost() + ":" + secretValue.getPort()
                        + "/" + secretValue.getDbname())
                .build();
    }

    private SecretValue getSecretValue() {

        String secretName = "demodb/test";
        String region = "us-east-1";

        // Create a Secrets Manager client
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();

        String secret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            return gson.fromJson(secret, SecretValue.class);
        }
        return null;
    }
}
