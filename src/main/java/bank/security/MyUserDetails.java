package bank.security;

import bank.entity.user_management.Role;
import bank.repository.user_management.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import bank.entity.user_management.User;
import bank.repository.user_management.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserRolesRepository userRolesRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final User user = userRepository.findByUserEmail(email);

    if (user == null) {
      throw new UsernameNotFoundException("User '" + email + "' not found");
    }

    //TODO: This is to replace spring role management with custom role management
    List<Role> roles = new ArrayList<>();
    roles.add(Role.ROLE_ADMIN);
    roles.add(Role.ROLE_CLIENT);

    return org.springframework.security.core.userdetails.User//
            .withUsername(email)//
            .password(user.getEncryptedPassword())//
            .authorities(roles)//
            .accountExpired(false)//
            .accountLocked(false)//
            .credentialsExpired(false)//
            .disabled(false)//
            .build();
  }

}
