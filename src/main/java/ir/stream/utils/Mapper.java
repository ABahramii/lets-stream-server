package ir.stream.utils;

import ir.stream.dto.UserDTO;
import ir.stream.entity.User;

import java.util.List;

public class Mapper {

    public static List<UserDTO> userDtoMapper(List<User> userList) {
        return userList.stream().map(UserDTO::new).toList();
    }
}
