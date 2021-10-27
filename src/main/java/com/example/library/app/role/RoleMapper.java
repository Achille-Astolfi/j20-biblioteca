package com.example.library.app.role;

import com.example.library.model.role.RoleResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    // mancano delle annotation
    @Mapping(target = "id", source = "roleId")
    @Mapping(target = "code", source = "roleCode")
    RoleResource toResource(Role entity);

    // qui no
    List<RoleResource> toResourceList(List<Role> entities);
}
