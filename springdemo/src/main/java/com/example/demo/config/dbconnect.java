package com.example.demo.config;

import java.sql.*;

public class dbconnect {

    public static void main(String[] args) {
        // Thông tin kết nối
        String url = "jdbc:sqlserver://THANHTAM:1433;databaseName=JPA;encrypt=true;trustServerCertificate=true";
        String user = "sa";
        String pass = "1234";

        // Câu lệnh SQL
        String query = "SELECT * FROM Users";

        // Kết nối và truy vấn
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("ID | Username | Email | FullName | Password | Avatar | RoleID | Phone | CreatedDate");
            System.out.println("--------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("userName");
                String email = rs.getString("email");
                String fullname = rs.getString("fullName");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");
                int roleid = rs.getInt("roleid");
                String phone = rs.getString("phone");
                Timestamp createdDate = rs.getTimestamp("createdDate");

                System.out.println(id + " | " + username + " | " + email + " | " + fullname + " | " 
                                   + password + " | " + avatar + " | " + roleid + " | " + phone + " | " + createdDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
