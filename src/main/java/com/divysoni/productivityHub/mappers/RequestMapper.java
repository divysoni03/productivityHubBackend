package com.divysoni.productivityHub.mappers;

import com.divysoni.productivityHub.dto.misc.RequestDto;
import com.divysoni.productivityHub.entities.misc.Request;

public interface RequestMapper {
    RequestDto toDto(Request request);
}
