package org.example.orishw2.service;


import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.User;
import org.example.orishw2.exception.UserAlreadyExistException;
import org.example.orishw2.mapper.UserMapper;
import org.example.orishw2.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.example.orishw2.dto.RegistrationForm;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public void createUser(RegistrationForm form) {
        userRepository.findByUsername(form.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("user exist");
                });

        userRepository.findByEmail(form.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("user exist");
                });

        String encode = passwordEncoder.encode(form.getPassword());
        User user = userMapper.mapToUser(form, encode);
        userRepository.save(user);
        mailService.sendEmailForConfirm(user.getEmail(), user.getConfirmCode());

    }
}
