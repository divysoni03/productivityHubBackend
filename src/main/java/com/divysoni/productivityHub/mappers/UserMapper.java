package com.divysoni.productivityHub.mappers;

import com.divysoni.productivityHub.dto.user.UserDto;
import com.divysoni.productivityHub.entities.users.User;

public interface UserMapper {
    User fromDto(UserDto userDto);
    UserDto toDto(User user);
}
