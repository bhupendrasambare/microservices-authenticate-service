package com.service.authenticate.config;

import com.service.authenticate.dto.response.UserDto;
import com.service.authenticate.repository.RolesRepository;
import com.service.authenticate.repository.UsersRepository;
import com.service.authenticate.dto.response.Response;
import com.service.authenticate.model.Roles;
import com.service.authenticate.model.Users;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Log4j2
public class UserService {
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    UsersRepository repository;

    @Autowired
    AuthConfig authConfig;

    public ResponseEntity<Response> createUser(UserDto request) {
        Response response = new Response();
        try {
            Users users = new Users(request);
            Roles roles = rolesRepository.findByName(request.getRoleName()).orElseThrow(() -> new Exception("Role not found"));
            users.setRoles(roles);
            users.setPassword(authConfig.passwordEncoder().encode(request.getPassword()));
            users = repository.save(users);
            response.setData(users);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public String generateToken(String username,String password) {
        Users users = repository.findByEmail(username).orElse(null);
        if(users!=null){
            Authentication authentication = authConfig.authenticationProvider().authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(authentication.isAuthenticated()){
                return authConfig.GenerateToken(username);
            }
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }else{
            throw new UsernameNotFoundException("User not found");
        }
    }

    public void validateToken(String token) throws Exception {
        if(!authConfig.validateToken(token)){
            throw new UsernameNotFoundException("Invalid token");
        }
    }
}
