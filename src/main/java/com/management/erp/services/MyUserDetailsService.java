package com.management.erp.services;

import com.management.erp.models.repository.UserModel;
import com.management.erp.models.services.MyUserDetails;
import com.management.erp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService  implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByEmail(username);

        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
        return user.map(MyUserDetails::new).get();
    }
}
