package de.stphngrtz;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.stream.Collectors;

import static spark.Spark.get;

public class Main {

    private static final String db_addr = System.getenv("DB_PORT_5432_TCP_ADDR");
    private static final String db_port = System.getenv("DB_PORT_5432_TCP_PORT");
    private static final String db_username = System.getenv("DB_USERNAME");
    private static final String db_password = System.getenv("DB_PASSWORD");
    private static final String db_retries = System.getenv("DB_RETRIES");
    private static final String db_pause_between_retries = System.getenv("DB_PAUSE_BETWEEN_RETRIES");

    public static void main(String[] args) {
        if (db_addr == null || db_port == null || db_username == null || db_password == null || db_retries == null || db_pause_between_retries == null)
            throw new IllegalStateException("db environment variables not properly set");

        Sql2o sql2o = DB.init(db_addr, db_port, db_username, db_password, Integer.valueOf(db_retries), Long.valueOf(db_pause_between_retries));

        get("/hello", (req, res) -> "Hello World! (service2)");

        get("/messages", (request, response) -> {
            try (Connection c = sql2o.open()) {
                return c.createQuery("" +
                        "SELECT message" +
                        "  FROM service2.messages")
                        .executeAndFetch(String.class)
                        .stream().collect(Collectors.joining(", "));
            }
        });
    }
}
