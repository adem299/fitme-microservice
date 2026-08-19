package com.fitme.activityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fitme.activityservice.dto.ActivityRequest;
import com.fitme.activityservice.dto.ActivityResponse;
import com.fitme.activityservice.model.Activity;
@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @Mapping(target = "type", source = "activityType")
    ActivityResponse toActivityResponse(Activity activity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activityType", source  = "type")
    Activity toActivity(ActivityRequest request);
}
