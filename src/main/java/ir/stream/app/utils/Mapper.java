package ir.stream.app.utils;

import ir.stream.app.dto.UserDTO;
import ir.stream.app.entity.User;

import java.util.List;

public class Mapper {

    public static List<UserDTO> userDtoMapper(List<User> userList) {
        return userList.stream().map(UserDTO::new).toList();
    }
}
