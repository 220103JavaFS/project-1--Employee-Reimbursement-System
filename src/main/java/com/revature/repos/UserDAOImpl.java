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
            String sql = "SELECT ers_username, ers_password, ers_user_role FROM ers_users AS users JOIN ers_user_roles AS roles " +
                    "ON users.user_role_id = roles.ers_user_role_id " +
                    "WHERE users.ers_username = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);

            //parameter index indicates the "?" that will be replaced with the value given as the second input.
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            UserDTO user = new UserDTO();

            while (rs.next()){
                user.username = rs.getString("ers_username");
                user.password = rs.getString("ers_password");
                user.userRole = rs.getString("ers_user_role");
            }

            return user;

        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
