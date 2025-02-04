package com.project.ITAM.Service;

import com.project.ITAM.Model.Users;
import com.project.ITAM.Model.UsersRequest;

import java.util.List;

public interface UsersService {
    public Users createUser(UsersRequest usersRequest);

    public Users getUsersById(Long userId);

    public Users updateUsersById(UsersRequest usersRequest,Long userId);

    public void deleteUsersById(Long userId);

    public Users addUserToGroup(Long userId,Long GroupId);

    public List<Users> getAllUsers();

}
