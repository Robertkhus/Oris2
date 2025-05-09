package org.example.orishw2.service;

import lombok.RequiredArgsConstructor;
import org.example.orishw2.entity.User;
import org.example.orishw2.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public boolean confirmUser(String confirmCode) {
        User user = userRepository.findUserByConfirmCode(confirmCode).orElseThrow();
        user.setStatus("CONFIRMED");
        user.setConfirmCode(null);
        return true;
    }

}
