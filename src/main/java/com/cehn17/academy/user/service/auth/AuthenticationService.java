package com.cehn17.academy.user.service.auth;

import com.cehn17.academy.exception.ObjectNotFoundException;
import com.cehn17.academy.student.dto.StudentRegisterRequest;
import com.cehn17.academy.student.entity.Student;
import com.cehn17.academy.student.mapper.StudentMapper;
import com.cehn17.academy.student.repository.StudentRepository;
import com.cehn17.academy.teacher.dto.TeacherRegisterRequest;
import com.cehn17.academy.teacher.entity.Teacher;
import com.cehn17.academy.teacher.mapper.TeacherMapper;
import com.cehn17.academy.teacher.repository.TeacherRepository;
import com.cehn17.academy.user.dto.LoginRequest;
import com.cehn17.academy.user.dto.LoginResponse;
import com.cehn17.academy.user.entity.User;
import com.cehn17.academy.user.mapper.UserMapper;
import com.cehn17.academy.user.repository.UserRepository;
import com.cehn17.academy.user.service.UserService;
import com.cehn17.academy.user.util.Role;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final UserMapper userMapper;
    private final StudentMapper studentMapper;

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    // LOGIN: Valida credenciales y retorna JWT
    public LoginResponse login(LoginRequest loginRequest) {
        // 1. Crear el token de autenticación para Spring
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()
        );

        // 2. El Manager valida contra el UserDetailsService y PasswordEncoder
        authenticationManager.authenticate(authentication);

        // 3. Si no lanzó excepción, el usuario es válido. Lo buscamos para el JWT.
        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new ObjectNotFoundException("Usuario no encontrado"));

        // 4. Generamos el token con claims extra (rol y permisos)
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new LoginResponse(jwt, user.getUsername(), user.getRole().name());
    }

    @Transactional
    public LoginResponse registerStudent(StudentRegisterRequest request) {

        User user = userMapper.toEntity(request.user());
        user.setPassword(passwordEncoder.encode(request.user().password()));
        user.setRole(Role.STUDENT);
        User savedUser = userRepository.save(user);

        Student student = studentMapper.toEntity(request);
        student.setUser(savedUser);
        studentRepository.save(student);

        String jwtToken = jwtService.generateToken(savedUser, new HashMap<>());

        return LoginResponse.builder()
                .jwt(jwtToken)
                .username(savedUser.getUsername())
                .role(String.valueOf(savedUser.getRole()))
                .build();
    }

    @Transactional
    public LoginResponse registerTeacher(TeacherRegisterRequest request) {

        User user = userMapper.toEntity(request.user());
        user.setPassword(passwordEncoder.encode(request.user().password()));
        user.setRole(Role.TEACHER);
        User savedUser = userRepository.save(user);

        Teacher teacher = teacherMapper.toEntity(request);
        teacher.setUser(user);
        teacherRepository.save(teacher);

        String jwtToken = jwtService.generateToken(savedUser, new HashMap<>());

        return LoginResponse.builder()
                .jwt(jwtToken)
                .username(savedUser.getUsername())
                .role(savedUser.getRole().name())
                .build();
    }

    public boolean validateToken(String jwt) {

        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }

    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", user.getRole().name());
        extraClaims.put("permissions", user.getAuthorities());
        return extraClaims;
    }


}
