package com.revature.repos;

import com.revature.models.UserDTO;
import com.revature.utils.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    Logger logger = LoggerFactory.getLogger("UserDao logger");

    @Override
    public UserDTO login(String username) {
        try(Connection conn = ConnectionUtil.getConnection()){
            String sql = "SELECT ers_username, ers_password, ers_user_role, ers_users_id FROM ers_users AS users JOIN ers_user_roles AS roles " +
                    "ON users.user_role_id = roles.ers_user_role_id " +
                    "WHERE users.ers_username = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()){
                logger.info("The query executed and returned a record");
                UserDTO user = new UserDTO();
                user.username = rs.getString("ers_username");
                user.password = rs.getString("ers_password");
                user.userRole = rs.getString("ers_user_role");
                user.userID = rs.getInt("ers_users_id");
                return user;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        logger.debug("No UserDTO object was created");
        return null;
    }
}
