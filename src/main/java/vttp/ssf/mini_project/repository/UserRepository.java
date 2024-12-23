package vttp.ssf.mini_project.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import vttp.ssf.mini_project.model.User;

@Repository
public class UserRepository {

@Autowired
@Qualifier("redis-object")
private RedisTemplate<String,Object> template;

    private final String KEY = "USER";

    // Save a user
    public void save(User user) {
        template.opsForHash().put(KEY,user.getEmail(),user);
    }

    // Retrieve a user by email
    public User findByEmail(String email) {
        return (User) template.opsForHash().get(KEY, email);
    }

}