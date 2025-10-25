package com.divysoni.productivityHub.mappers.impl;

import com.divysoni.productivityHub.dto.misc.RequestDto;
import com.divysoni.productivityHub.entities.misc.Request;
import com.divysoni.productivityHub.mappers.RequestMapper;
import org.springframework.stereotype.Component;

@Component
public class RequestMapperImpl implements RequestMapper {
    @Override
    public RequestDto toDto(Request request) {
        return new RequestDto(
                request.getId().toString(),
                request.getTaskList().getId().toString(),
                request.getSenderId().toString(),
                request.getCreated(),
                request.getExpiration()
        );
    }
}
