package com.project.ITAM.Service;

import com.project.ITAM.Model.UserDTO;
import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;

import java.util.List;

public interface UsersService {
    public String createUser(UsersRequest usersRequest);

    public UserDTO getUsersById(Long userId);

    public String updateUsersById(UsersRequest usersRequest,Long userId);

    public String updateUsersByRoleId(Long userId,String roleId);

    public void deleteUsersById(Long userId);

    public String addUserToGroup(Long userId,Long GroupId);

    public List<UserDTO> getAllUsers();

    public int countUsers();

}
