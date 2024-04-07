package com.team100.springboot.service;

import com.team100.springboot.dto.UserDto;
import com.team100.springboot.entity.Role;
import com.team100.springboot.entity.User;
import com.team100.springboot.repository.RoleRepository;
import com.team100.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFullName());
        user.setEmail(userDto.getEmail());

        //encrypt the password using spring security
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserType(userDto.getUserType().name());
            user.setBusinessAddress(userDto.getCompanyAddress());
            user.setBusinessRegistrationNumber(userDto.getBusinessRegistrationNumber());


        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFullName(name[0]);
        userDto.setEmail(user.getEmail());
        userDto.setCompanyAddress(user.getBusinessAddress()); // Set company address
        userDto.setBusinessRegistrationNumber(user.getBusinessRegistrationNumber());
        return userDto;
    }
}
