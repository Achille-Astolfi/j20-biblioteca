package com.example.library.app.role;

import com.example.library.model.role.RoleResource;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {


  @Mapping(target = "id", source = "roleId")
  @Mapping(target = "code", source = "roleCode")
  RoleResource toResource(Role entity);

  //se sopra è dichiarato il metodo di mapping er i singoli oggetti,
  //posso dichiarare un metodo che va da lista a lista e lo fa in automatico
  List<RoleResource> toResourceList(List<Role> entities);

}
