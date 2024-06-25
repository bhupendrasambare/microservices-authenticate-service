package com.service.authenticate.model.dto;

import com.service.authenticate.model.Users;
import lombok.Data;

@Data
public class UserDto extends Users {
    private String roleName;
}
