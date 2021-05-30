package com.epam.expositions.security;

import com.epam.expositions.entity.Permission;
import com.epam.expositions.entity.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class SecurityUser extends org.springframework.security.core.userdetails.User {

    public SecurityUser(String username, String password, List<SimpleGrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public static UserDetails fromUser(User user) {
        return new SecurityUser(
                user.getLogin(),
                user.getPassword(),
                Permission.matchPermissions(user.getRole())
                        .stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                        .collect(Collectors.toList())
        );
    }
}
