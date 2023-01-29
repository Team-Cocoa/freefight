package kr.teamcocoa.freefight.mysql;

import kr.teamcocoa.mysql.mysql.MySQL;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionDatabase {

    private static MySQL mysql;

    public static void registerMySQL(MySQL database) {
        if(mysql != null) {
            return;
        }
        mysql = database;
    }






}
