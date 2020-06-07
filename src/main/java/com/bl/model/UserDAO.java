package com.bl.model;

import com.bl.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static Connection con;
    private static PreparedStatement ps;
    public static User getUser(String emailid, String pwd) throws SQLException {
        User user = null;
        con= DatabaseConnector.getConnection();
        ps=con.prepareStatement("select * from mydatabase.newusers where emailId=? and password=?");
        ps.setString(1,emailid);
        ps.setString(2,pwd);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            user=new User();
            user.setName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
        }
        return user;
    }

    public static int insertUser(String username,String email,String password) throws SQLException {
        con= DatabaseConnector.getConnection();
        ps=con.prepareStatement("insert into mydatabase.newusers(name,emailId,password) values(?,?,?)");
        ps.setString(1,username);
        ps.setString(2,email);
        ps.setString(3,password);
        int status = ps.executeUpdate();
        return status;
    }

    public static User getUser(String emailId) throws SQLException {
        User user = null;
        con= DatabaseConnector.getConnection();
        ps=con.prepareStatement("select * from mydatabase.newusers where emailId=?");
        ps.setString(1,emailId);
        ResultSet resultSet = ps.executeQuery();
        while (resultSet.next()){
            user=new User();
            user.setName(resultSet.getString(1));
            user.setPassword(resultSet.getString(2));
        }
        return user;
    }
}
