package com.divysoni.taskManager.mappers.impl;

import com.divysoni.taskManager.dto.UserDto;
import com.divysoni.taskManager.entities.users.User;
import com.divysoni.taskManager.mappers.UserMapper;

public class UserMapperImpl implements UserMapper {
    @Override
    public User fromDto(UserDto userDto) {
        return new User(
                null,
                userDto.userName(),
                userDto.password(),
                userDto.email(),
                false,
                userDto.taskLists(),
                null
        );
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getTaskLists()
        );
    }
}
