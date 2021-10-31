package com.example.library.app.role;

import com.example.library.model.role.RoleResource;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper( componentModel = "spring")
public interface RoleMapper {

  // mancano delle annotations
  @Mapping(target = "id", source = "roleId")
  @Mapping(target = "code", source = "roleCode")
  RoleResource toResource(Role entity);


  // qui non mancano delle annotations
  List<RoleResource> toResourceList(List<Role> entities);

}
