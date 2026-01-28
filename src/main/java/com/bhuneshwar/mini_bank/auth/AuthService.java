package com.bhuneshwar.mini_bank.auth;


import com.bhuneshwar.mini_bank.dto.AuthResponse;
import com.bhuneshwar.mini_bank.dto.LoginRequest;
import com.bhuneshwar.mini_bank.dto.RegisterRequest;
import com.bhuneshwar.mini_bank.entity.AppUser;
import com.bhuneshwar.mini_bank.repository.UserRepository;
import com.bhuneshwar.mini_bank.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepo, BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }
     public String register(RegisterRequest req)
     {
         if(userRepo.existsByEmail(req.getEmail()))
         {
             return  "Already Register";
         }
         String hashPassword=encoder.encode(req.getPassword());
         AppUser user=new AppUser(req.getName(),req.getEmail(),hashPassword,"CUSTOMER");
         userRepo.save(user);
          return "Registration Successful";
     }
    public AuthResponse login(LoginRequest req) {

        AppUser user = userRepo.findByEmail(req.getEmail()).orElse(null);

        if (user == null) {
            throw new RuntimeException("User not found!");
        }

        boolean match = encoder.matches(req.getPassword(), user.getPassword());
        if (!match) {
            throw new RuntimeException("Invalid password!");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getEmail(), user.getRole());
    }
}
