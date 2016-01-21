package de.stphngrtz;

import org.flywaydb.core.Flyway;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.util.Date;
import java.util.logging.Logger;

public final class DB {

    private static final Logger logger = Logger.getLogger(DB.class.getName());

    private DB() {
    }

    public static Sql2o init(String addr, String port, String username, String password, Integer retries, Long pauseBetweenRetries) {
        Sql2o sql2o = new Sql2o("jdbc:postgresql://" + addr + ":" + port + "/" + username, username, password);
        if (waitForConnection(sql2o, retries, pauseBetweenRetries)) {
            migrate(sql2o.getDataSource());
            return sql2o;
        }
        else
            throw new IllegalStateException("[" + new Date() + "] could not establish connection to " + addr + ":" + port);
    }

    private static void migrate(DataSource dataSource) {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.migrate();
    }

    private static boolean waitForConnection(Sql2o sql2o, Integer retries, Long pauseBetweenRetries) {
        int retry = 0;
        while (retry++ < retries) {
            try (Connection c = sql2o.open()) {
                return true;
            } catch (RuntimeException e) {
                logger.warning("[" + new Date() + "] connection attempt " + retry + "/" + retries + " failed, waiting " + pauseBetweenRetries + "ms...");
                try {
                    Thread.sleep(pauseBetweenRetries);
                } catch (InterruptedException ignored) {
                }
            }
        }
        return false;
    }
}
