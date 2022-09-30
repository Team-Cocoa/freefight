package kr.teamcocoa.freefight.mysql;

import lombok.Getter;

import java.sql.*;
import java.text.MessageFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MySQL {

    private static String host = "localhost";
    private static String database = "freefight";
    private static String user = "root";
    private static String password = "root";
    private static String port = "3306";

    @Getter
    private static Connection connection;

    @Getter
    private static ScheduledExecutorService task = Executors.newSingleThreadScheduledExecutor();

    public static void connect() {
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection(MessageFormat.format(
                        "jdbc:mysql://{0}:{1}/{2}", host, port, database), user, password);
                task.scheduleAtFixedRate(() -> {
                    try(    PreparedStatement preparedStatement = getPreparedStatement("select 1");
                            ResultSet rs = preparedStatement.executeQuery()) {

                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }, 0, 1, TimeUnit.HOURS);
                System.out.println("[DeathMatch] Successfully connected to MySQL!");
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[DeathMatch] Failed to connect to MySQL!");
            }
        }
    }

    public static void disconnect() {
        if(isConnected()) {
            try {
                connection.close();
                System.out.println("[DeathMatch] Successfully disconnected to MySQL!");
            }
            catch (SQLException e) {
                e.printStackTrace();
                System.out.println("[DeathMatch] Failed to disconnect to MySQL!");
            }
        }
    }

    public static boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void update(String query) {
        if(isConnected()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(String query, Object... placeholders) {
        if(isConnected()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                for(int i = 0; i < placeholders.length; i++) {
                    preparedStatement.setObject(i + 1, placeholders[i]);
                }
                preparedStatement.executeUpdate();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static PreparedStatement getPreparedStatement(String query) {
        try {
            return connection.prepareStatement(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement getPreparedStatement(String query, Object... placeholders) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for(int i = 0; i < placeholders.length; i++) {
                preparedStatement.setObject(i + 1, placeholders[i]);
            }
            return preparedStatement;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
