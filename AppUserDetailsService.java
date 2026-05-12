package com.example.docstore.security;

import com.example.docstore.user.UserEntity;
import com.example.docstore.user.UserRepository;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
  private final UserRepository users;

  public AppUserDetailsService(UserRepository users) {
    this.users = users;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity u =
        users
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new User(u.getUsername(), u.getPasswordHash(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }
}

