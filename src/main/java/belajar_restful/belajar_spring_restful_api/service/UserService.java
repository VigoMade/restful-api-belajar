package belajar_restful.belajar_spring_restful_api.service;

import belajar_restful.belajar_spring_restful_api.entity.User;

import belajar_restful.belajar_spring_restful_api.model.RegisterUserRequest;
import belajar_restful.belajar_spring_restful_api.model.UpdateUserRequest;
import belajar_restful.belajar_spring_restful_api.model.UserResponse;
import belajar_restful.belajar_spring_restful_api.model.WebResponse;
import belajar_restful.belajar_spring_restful_api.repository.UserRepository;
import belajar_restful.belajar_spring_restful_api.security.BCrypt;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;


    @Transactional
    public void register(RegisterUserRequest request){
     validationService.validate(request);

     if(userRepository.existsById(request.getUsername())){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Username already exists");
     }

     User user = new User();
     user.setUsername(request.getUsername());
     user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
     user.setName(request.getName());

     userRepository.save(user);
    }

    public UserResponse get(User user){
        return UserResponse.builder().username(user.getUsername()).name(user.getName()).build();
    }


    @Transactional
    public UserResponse update(User user, UpdateUserRequest request){
        validationService.validate(request);

        if(Objects.nonNull(request.getName())){
            user.setName(request.getName());
        }

        if(Objects.nonNull(request.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        userRepository.save(user);
        log.info("USER : {}",user.getName());

        return UserResponse.builder()
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }


}
