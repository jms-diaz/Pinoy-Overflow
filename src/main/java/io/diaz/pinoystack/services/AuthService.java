package io.diaz.pinoystack.services;

import io.diaz.pinoystack.dto.RegisterRequest;
import io.diaz.pinoystack.exceptions.PinoyStackException;
import io.diaz.pinoystack.models.NotificationEmail;
import io.diaz.pinoystack.models.User;
import io.diaz.pinoystack.models.VerificationToken;
import io.diaz.pinoystack.repo.UserRepo;
import io.diaz.pinoystack.repo.VerificationTokenRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final VerificationTokenRepo verificationTokenRepo;
    private final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepo.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your account to continue",
                user.getEmail(),
                "Thank you for signing up to Pinoy Stack. Please click on the link below to activate your account: "
                        + "http://localhost:2020/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepo.save(verificationToken);
        return token;

    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByToken(token);
        verificationToken.orElseThrow(() -> new PinoyStackException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        @NotBlank(message = "Username is required")
        String username = verificationToken.getUser().getUsername();
        User user = userRepo.findByUsername(username).orElseThrow(() -> new PinoyStackException("User " + username + "is not found"));
        user.setEnabled(true);
        userRepo.save(user);
    }
}
