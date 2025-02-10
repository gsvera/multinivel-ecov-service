package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.generics.ResetPasswordTokenDTO;
import com.ecov.multinivel.entity.ResetPasswordToken;
import com.ecov.multinivel.repository.ResetPasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetTokenService {
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public String _GenerateToken(String email) {
        UUID uuid = UUID.randomUUID();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setToken(uuid.toString());
        resetPasswordToken.setEmail(email);
        resetPasswordToken.setActive(true);
        resetPasswordToken.setCreatedDate(Timestamp.from(Instant.now()));

        resetPasswordTokenRepository.save(resetPasswordToken);

        return uuid.toString();
    }

    public ResetPasswordTokenDTO _GetToken(String token) {
        Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

        return new ResetPasswordTokenDTO(resetPasswordToken.get());
    }

    public boolean _IsValidToken(ResetPasswordTokenDTO resetPasswordTokenDTO) {
        if(resetPasswordTokenDTO.active == true && resetPasswordTokenDTO.getCreatedDate() != null) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            long differentTime = currentTime.getTime() - resetPasswordTokenDTO.getCreatedDate().getTime();
            long differentTimeToken = differentTime / (1000 * 60 * 60);// Para validar que sea menor de una hora
            System.out.println(differentTimeToken);
            if(differentTimeToken < 1) {
                return true;
            }
        }
        this._DeleteTokenById(resetPasswordTokenDTO.getId());
        return false;
    }

        public void _DeleteTokenById(Long idToken) {
        resetPasswordTokenRepository.deleteById(idToken);
    }
}
