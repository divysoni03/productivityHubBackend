package com.divysoni.taskManager.mappers;

import com.divysoni.taskManager.dto.UserDto;
import com.divysoni.taskManager.entities.users.User;

public interface UserMapper {

    User fromDto(UserDto userDto);

    UserDto toDto(User user);
}
