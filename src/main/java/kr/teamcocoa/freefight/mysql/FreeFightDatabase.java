package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.core.mysql.MySQL;
import kr.teamcocoa.core.mysql.MySQLManager;
import lombok.Getter;

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

        mySQL.connect();
    }

}
