package com.ecov.multinivel.service;

import com.ecov.multinivel.config.ConstantsConfig;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.entity.User;
import com.ecov.multinivel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AffiliateService {
    private final UserRepository userRepository;
    private final ConstantsConfig constantsConfig;
    public ResponseDTO _GetAllAffiliateTree(){
        List<Object[]> results = userRepository.findUserHierarchy();
        Map<String, UserDTO> userMap = new HashMap<>();
        List<UserDTO> rootUsers = new ArrayList<>();

        // Crear UserDTOs y mapearlos por ID
        for (Object[] row : results) {
            String id = (String) row[0];
            String firstName = (String) row[1];
            String lastName = (String) row[2];
            String referenceParent = row[3] != null ? (String) row[3] : null;
            Timestamp createdDate = (Timestamp) row[4];
            int nivel = (int) row[5];

            UserDTO user = new UserDTO(id, firstName, lastName,  nivel, createdDate);
            userMap.put(id, user);

            // Si no tiene padre, es un nodo raíz
            if (referenceParent == null) {
                rootUsers.add(user);
            } else {
                // Si tiene padre, agregarlo a la lista `child` del padre
                UserDTO parent = userMap.get(referenceParent);
                if (parent != null) {
                    parent.getChild().add(user);
                }
            }
        }
        return ResponseDTO.builder().items(rootUsers).build();
    }
    public ResponseDTO _GetDataAffiliate(){
        List<User> userAffiliate = userRepository.findAllAffiliate(constantsConfig.getWorkGroupIdAffiliate());
        return ResponseDTO.builder()
                .items(
                        userAffiliate
                            .stream()
                            .map(item -> new UserDTO(item))
                            .collect(Collectors.toList()))
                .build();
    }
}
