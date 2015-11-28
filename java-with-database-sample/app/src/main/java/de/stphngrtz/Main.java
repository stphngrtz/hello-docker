package de.stphngrtz;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static spark.Spark.get;

public class Main {

    public static class DataDTO {
        public Long id;
        public String name;
    }

    public static void main(String[] args) {

        String db_url = System.getenv("DB_PORT_5432_TCP_ADDR");
        String db_port = System.getenv("DB_PORT_5432_TCP_PORT");
        String db_username = "stephan";
        String db_password = "mysecretpassword";
        String db_database = db_username;

        /*
        DB_NAME = /app/db
        DB_PORT = tcp://172.17.0.2:5432
        DB_PORT_5432_TCP = tcp://172.17.0.2:5432
        DB_PORT_5432_TCP_PROTO = tcp
        DB_PORT_5432_TCP_PORT = 5432
        DB_PORT_5432_TCP_ADDR = 172.17.0.2
        */

        if (db_url == null || db_port == null)
            throw new IllegalStateException("environment variables not properly set");

        Sql2o sql2o = new Sql2o("jdbc:postgresql://"+db_url+":"+db_port+"/"+db_database, db_username, db_password);

        get("/hello", (req, res) -> {
            StringBuilder sb = new StringBuilder();
            sb.append("hello!");

            try(Connection con = sql2o.open()) {
                List<DataDTO> dataDTOs = con.createQuery("SELECT id, name FROM data").executeAndFetch(DataDTO.class);
                dataDTOs.forEach(dataDTO -> {
                    sb.append("<br />");
                    sb.append(dataDTO.id).append(": ").append(dataDTO.name);
                });
            }

            return sb.toString();
        });
    }
}
