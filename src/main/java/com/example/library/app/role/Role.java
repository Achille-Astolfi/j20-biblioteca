package com.example.library.app.role;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Role {
    @Id
    private Long roleId;
    private String roleCode;
    private String roleDescription;
}
