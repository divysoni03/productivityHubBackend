package com.divysoni.productivityHub.mappers.impl;

import com.divysoni.productivityHub.dto.UserDto;
import com.divysoni.productivityHub.entities.users.User;
import com.divysoni.productivityHub.mappers.UserMapper;
import org.bson.types.ObjectId;

public class UserMapperImpl implements UserMapper {
    @Override
    public User fromDto(UserDto userDto) {
        return new User(
                new ObjectId(userDto.id()),
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
                user.getId().toString(),
                user.getUserName(),
                user.getPassword(),
                user.getEmail(),
                user.getTaskLists()
        );
    }
}
