package com.sistema.blog.controller;

import com.sistema.blog.DTO.JWTAuthResponseDTO;
import com.sistema.blog.DTO.LoginDTO;
import com.sistema.blog.DTO.RegistroDTO;
import com.sistema.blog.model.Rol;
import com.sistema.blog.model.Usuario;
import com.sistema.blog.repository.RolRepository;
import com.sistema.blog.repository.UsuarioRepository;
import com.sistema.blog.seguridad.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/iniciarSesion")
    public ResponseEntity<JWTAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Obtenemos el token de jwtTokenProvider

        String token = jwtTokenProvider.generarToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
        if(usuarioRepository.existsByUsername(registroDTO.getUsername())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        if(usuarioRepository.existsByEmail(registroDTO.getEmail())){
            return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(registroDTO.getUsername());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
        //obtener un solo rol
        usuario.setRoles(Collections.singleton(roles));

        usuarioRepository.save(usuario);

        return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.OK);
    }
}
