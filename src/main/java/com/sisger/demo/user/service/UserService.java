package com.sisger.demo.user.service;

import com.sisger.demo.user.domain.RegisterDTO;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(RegisterDTO data){
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = User.builder()
                .email(data.getEmail())
                .role(data.getRole())
                .cpf(data.getCpf())
                .name(data.getName())
                .companyId(data.getCompanyId())
                .password(encryptedPassword)
                .build();

        this.userRepository.save(newUser);
    }

}
