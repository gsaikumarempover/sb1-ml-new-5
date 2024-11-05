package com.mladmin.portal.resolver;

import com.mladmin.portal.entity.User;
import com.mladmin.portal.service.UserService;
import com.mladmin.portal.dto.UserUpdateInput;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserResolver {

    private final UserService userService;

    @QueryMapping
    public List<User> users() {
        return userService.getAllUsers();
    }

    @QueryMapping
    public User user(@Argument Long id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public User me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByUsername(username);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UserUpdateInput input) {
        return userService.updateUser(id, input);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }
}