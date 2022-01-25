
package com.revature.repos;

import com.revature.models.UserDTO;

public interface UserDAO {

    UserDTO login(String username);
}

