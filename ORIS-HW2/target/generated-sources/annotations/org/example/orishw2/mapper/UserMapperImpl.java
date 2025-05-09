package org.example.orishw2.mapper;

import javax.annotation.processing.Generated;
import org.example.orishw2.dictionary.Role;
import org.example.orishw2.dto.RegistrationForm;
import org.example.orishw2.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-09T23:13:15+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User mapToUser(RegistrationForm form, String password) {
        if ( form == null && password == null ) {
            return null;
        }

        User user = new User();

        if ( form != null ) {
            user.setUsername( form.getUsername() );
            user.setEmail( form.getEmail() );
        }
        user.setPassword( password );
        user.setRole( Role.USER );

        return user;
    }
}
