package dao;

import context.DBContext;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    private static final String SELECT_ALL_USER = "select * from user where user_email = ? and user_pass = ?";
    private static final String UPDATE_USER = "update user set  user_pass = ? where id = ? ";
    private static final String SELECT_USER_EMAIL = "select * from user where user_email = ?";
    private static final String INSERT_USER = "insert into `user` (first_name,last_name , user_email, user_pass, isAdmin) values(?,?,?,?,?);";
    private static final String SELECT_USER = "select * from user";
    private static final String UPDATE_ISADMIN = "update user set isAdmin= ? where id = ?";

    private static final String SELECT_ISMADIN_TRUE = "select * from user where user_email = ? and user_pass=? and isAdmin ='true'";

    public User checkUser(String user_email, String user_pass) throws Exception {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(SELECT_ALL_USER);
        ps.setString(1, user_email);
        ps.setString(2, user_pass);
        rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5),rs.getString(6),rs.getString(7));
            return user;
        }
        return null;
    }

    public User checkAdmin(String user_email , String user_pass) throws SQLException {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(SELECT_ISMADIN_TRUE);
        ps.setString(1, user_email);
        ps.setString(2, user_pass);
        rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt(1), rs.getString(2),
                    rs.getString(3), rs.getString(4),
                    rs.getString(5),rs.getString(6));
            return user;
        }
        return null;
    }

    public void UpdateUser(int id,String user_pass) throws Exception {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(UPDATE_USER);
        ps.setString(1, user_pass);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public User checkAcc(String user_email) throws SQLException {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(SELECT_USER_EMAIL);
        ps.setString(1, user_email);
        rs = ps.executeQuery();
        while (rs.next()) {
            User user = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6));
            return user;
        }
        return null;
    }

    public void signup(String first_name , String last_name ,String user_email, String user_pass) throws SQLException {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(
                INSERT_USER);
        ps.setString(1,first_name);
        ps.setString(2,last_name);
        ps.setString(3, user_email);
        ps.setString(4, user_pass);
        ps.setString(5, "False");
        ps.executeUpdate();
    }

    public List<User> getUser() throws Exception {
        List<User> list = new ArrayList<>();
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(SELECT_USER);
        rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6)));
        }
        return list;
    }

    public void setAdmin(int id, String isAdmin) throws Exception {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(UPDATE_ISADMIN);
        ps.setInt(2, id);
        ps.setString(1, isAdmin.toUpperCase());
    }


}
