package ir.stream.utils;

import ir.stream.dto.Message;
import ir.stream.dto.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.random.RandomGenerator;

public class CacheUtils {
    public static final Map<Long, Set<User>> roomMemberMap = new HashMap<>();
    public static final Map<Long, Set<Message>> roomMessageMap = new HashMap<>();

    static {
        User user = new User(RandomGenerator.getDefault().nextLong(), "amir");
//        Room room = new Room(RandomGenerator.getDefault().nextLong(), "amir", user);
        roomMemberMap.put(1L, new HashSet<>());
        roomMessageMap.put(1L, new HashSet<>());
    }
}
