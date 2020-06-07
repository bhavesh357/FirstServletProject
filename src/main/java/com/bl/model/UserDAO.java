package com.bl.model;

import com.bl.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static Connection con;
    private static PreparedStatement ps;
    public static User getUser(String name, String pwd) throws SQLException {
        User user = null;
        con= DatabaseConnector.getConnection();
        ps=con.prepareStatement("select * from mydatabase.users where name=? and password=?");
        ps.setString(1,name);
        ps.setString(2,pwd);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            user=new User();
            user.setName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
        }
        return user;
    }

    public static int insertUser(String username, String password) throws SQLException {
        con= DatabaseConnector.getConnection();
        ps=con.prepareStatement("insert into mydatabase.users values(?,?)");
        ps.setString(1,username);
        ps.setString(2,password);
        int status = ps.executeUpdate();
        return status;
    }

    public static User getUser(String username) throws SQLException {
        User user = null;
        con= DatabaseConnector.getConnection();
        ps=con.prepareStatement("select * from mydatabase.users where name=?");
        ps.setString(1,username);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            user=new User();
            user.setName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
        }
        return user;
    }
}
