package de.stphngrtz;

import org.sql2o.Connection;
import org.sql2o.ResultSetHandler;
import org.sql2o.Sql2o;
import spark.utils.StringUtils;

import java.util.stream.Collectors;

import static spark.Spark.get;

public class Main {

    private static final String db_addr = System.getenv("POSTGRES2_PORT_5432_TCP_ADDR");
    private static final String db_port = System.getenv("POSTGRES2_PORT_5432_TCP_PORT");
    private static final String db_username = System.getenv("POSTGRES2_USERNAME");
    private static final String db_password = System.getenv("POSTGRES2_PASSWORD");
    private static final String db_retries = System.getenv("POSTGRES2_RETRIES");
    private static final String db_pause_between_retries = System.getenv("POSTGRES2_PAUSE_BETWEEN_RETRIES");
    private static final String service1_addr = System.getenv("SERVICE1_PORT_4567_TCP_ADDR");
    private static final String service1_port = System.getenv("SERVICE1_PORT_4567_TCP_PORT");
    private static final String service2_addr = System.getenv("SERVICE2_PORT_4567_TCP_ADDR");
    private static final String service2_port = System.getenv("SERVICE2_PORT_4567_TCP_PORT");

    public static void main(String[] args) {
        if (db_addr == null || db_port == null || db_username == null || db_password == null || db_retries == null || db_pause_between_retries == null)
            throw new IllegalStateException("db environment variables not properly set");
        if (service1_addr == null || service1_port == null || service2_addr == null || service2_port == null)
            throw new IllegalStateException("service environment variables not properly set");

        Sql2o sql2o = DB.init(db_addr, db_port, db_username, db_password, Integer.valueOf(db_retries), Long.valueOf(db_pause_between_retries));

        get("/hello", (req, res) -> "Hello World! (service3)");

        get("/discover", (request, response) -> {
            try (Connection c = sql2o.open()) {

                Integer countService1 = c.createQuery("SELECT count(1) FROM service3.services s WHERE s.id = :id")
                        .addParameter("id", 1)
                        .executeScalar(Integer.class);

                if (countService1 == 0) {
                    String hostService1 = service1_addr + ":" + service1_port;
                    String contentService1 = WS.get("http://" + hostService1 + "/hello");
                    if (!StringUtils.isEmpty(contentService1))
                        c.createQuery("INSERT INTO service3.services (id, host, service, response) VALUES (:id, :host, :service, :response)")
                                .addParameter("id", 1)
                                .addParameter("host", hostService1)
                                .addParameter("service", "hello")
                                .addParameter("response", contentService1)
                                .executeUpdate();
                }

                Integer countService2 = c.createQuery("SELECT count(1) FROM service3.services s WHERE s.id = :id")
                        .addParameter("id", 2)
                        .executeScalar(Integer.class);

                if (countService2 == 0) {
                    String hostService2 = service2_addr + ":" + service2_port;
                    String contentService2 = WS.get("http://" + hostService2 + "/hello");
                    if (!StringUtils.isEmpty(contentService2))
                        c.createQuery("INSERT INTO service3.services (id, host, service, response) VALUES (:id, :host, :service, :response)")
                                .addParameter("id", 2)
                                .addParameter("host", hostService2)
                                .addParameter("service", "hello")
                                .addParameter("response", contentService2)
                                .executeUpdate();
                }
            }
            response.redirect("/services");
            return "";
        });

        get("/services", (request, response) -> {
            try (Connection c = sql2o.open()) {
                return c.createQuery("" +
                        "SELECT host, service, response" +
                        "  FROM service3.services")
                        .executeAndFetch((ResultSetHandler<String>) rsh -> rsh.getString(1) + "/" + rsh.getString(2) + " = " + rsh.getString(3))
                        .stream().collect(Collectors.joining(", "));
            }
        });
    }
}
