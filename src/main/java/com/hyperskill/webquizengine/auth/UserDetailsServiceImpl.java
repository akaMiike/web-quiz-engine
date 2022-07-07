package com.hyperskill.webquizengine.auth;

import com.hyperskill.webquizengine.model.User;
import com.hyperskill.webquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userService.findByEmail(email);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Email not found: " + email);
        }

        return new UserDetailsImpl(user.get());
    }


}


