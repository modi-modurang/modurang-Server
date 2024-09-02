package modi.modurang.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import modi.modurang.service.EmailVerificationService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/send")
    public void sendVerificationCode(@RequestParam("email") @Valid @Email String email) {
        emailVerificationService.sendVerificationCode(email);
    }

    @PostMapping("/verify")
    public void verifyEmail(@RequestParam("email") String email,
                                              @RequestParam("code") String code) {
        emailVerificationService.verifyCode(email, code);
    }
}
