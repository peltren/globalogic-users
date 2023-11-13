package com.globallogic.users.infrastructure.output;

import com.globallogic.users.domain.models.User;
import com.globallogic.users.domain.models.UserPhone;
import com.globallogic.users.infrastructure.output.repositories.UserEntity;
import com.globallogic.users.infrastructure.output.repositories.UserPhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

    List<UserPhoneEntity> toUserPhoneEntities(List<UserPhone> userPhones);

    List<UserPhone> toUserPhones(List<UserPhoneEntity> userPhones);
}
