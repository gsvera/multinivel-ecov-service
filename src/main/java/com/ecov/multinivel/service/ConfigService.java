package com.ecov.multinivel.service;

import com.ecov.multinivel.dto.ConfigDTO;
import com.ecov.multinivel.entity.Config;
import com.ecov.multinivel.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigService {
    private static String descriptReference = "reference";
    private final ConfigRepository configRepository;
    public ConfigDTO _GetLastReference() {
        Optional<Config> config = configRepository.findByReference(descriptReference);
        if(config.isPresent()) {
            return new ConfigDTO(config.get());
        }
        return null;
    }

    public void _UpdateConfig(ConfigDTO configDTO) {
        Config config = new Config(configDTO);
        configRepository.save(config);
    }
}
