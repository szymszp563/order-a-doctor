package pl.polsl.orderadoctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.h2.tools.Server;


import java.sql.SQLException;

@SpringBootApplication
public class OrderADoctorApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderADoctorApplication.class, args);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
}
