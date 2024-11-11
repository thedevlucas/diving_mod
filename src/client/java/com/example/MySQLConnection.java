package com.example;

import java.sql.*;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/diving_mod";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        Diving_mod.LOGGER.info("Connecting to database...");
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(String playername, String UUID, int current_oxygen) {
        String query = "INSERT INTO players (playername, UUID, current_oxygen) VALUES (?, ?, ?)";
        Diving_mod.LOGGER.info(playername + " called");
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playername);
            stmt.setString(2, UUID);
            stmt.setInt(3, current_oxygen);
            stmt.executeUpdate();
            Diving_mod.LOGGER.info(playername + " inserted into database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
