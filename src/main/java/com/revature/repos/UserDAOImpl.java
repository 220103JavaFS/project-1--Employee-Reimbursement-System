package com.revature.repos;

import com.revature.models.UserDTO;
import com.revature.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Override
    public UserDTO login(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT ERS_USERNAME, ERS_PASSWORD, ERS_USER_ROLE FROM ERS_USERS AS users JOIN ERS_USER_ROLES AS roles " +
                    "ON users.USER_ROLE_ID = roles.ERS_USER_ROLE_ID" +
                    " WHERE users.ERS_USERNAME = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);

            //parameter index indicates the "?" that will be replaced with the value given as the second input.
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            UserDTO user = new UserDTO();

            while (rs.next()){
                user.username = rs.getString("ERS_USERNAME");
                user.password = rs.getString("ERS_PASSWORD");
                user.userRole = rs.getString("ERS_USER_ROLE");
            }

            return user;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
