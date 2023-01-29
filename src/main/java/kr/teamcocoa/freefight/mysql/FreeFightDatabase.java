package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.mysql.mysql.MySQL;
import kr.teamcocoa.mysql.mysql.MySQLManager;
import lombok.Getter;

import java.sql.*;
import java.text.MessageFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FreeFightDatabase {

    private static String database = "freefight";

    @Getter
    private static MySQL mySQL;

    public static void init() {
        if(mySQL != null) {
            return;
        }

        if(MySQLManager.hasConnection(database)) {
            mySQL = MySQLManager.getConnection(database);
        }
        else {
            mySQL = MySQLManager.createConnection(database);
        }

        SessionDatabase.registerMySQL(mySQL);

        mySQL.connect();
    }

}
