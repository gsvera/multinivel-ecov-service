package com.ecov.multinivel.service;

import com.ecov.multinivel.config.ConstantsConfig;
import com.ecov.multinivel.dto.ResponseDTO;
import com.ecov.multinivel.dto.UserDTO;
import com.ecov.multinivel.dto.generics.PageableResponseDTO;
import com.ecov.multinivel.entity.User;
import com.ecov.multinivel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        List<UserDTO> rootUsers = this._MakeTreeAffiliate(results, null);

        return ResponseDTO.builder().items(rootUsers).build();
    }
    public ResponseDTO _GetAffiliateByUser(String idUser) {
        List<Object[]> listUser = userRepository.findUserByUserId(idUser);
        List<UserDTO> rootUsers = this._MakeTreeAffiliate(listUser, idUser);
        return ResponseDTO.builder().items(rootUsers).build();
    }
    private List<UserDTO> _MakeTreeAffiliate(List<Object[]> dataAffiliate, String idUser){
        Map<String, UserDTO> userMap = new HashMap<>();
        List<UserDTO> rootUsers = new ArrayList<>();
        // Crear UserDTOs y mapearlos por ID
        for (Object[] row : dataAffiliate) {
            String id = (String) row[0];
            String firstName = (String) row[1];
            String lastName = (String) row[2];
            String referenceParent = row[3] != null ? (String) row[3] : null;
            Timestamp createdDate = (Timestamp) row[4];
            Boolean active = (Boolean) row[5];
            int nivel = (int) row[6];

            UserDTO user = new UserDTO(id, firstName, lastName,  nivel, createdDate, active);
            userMap.put(id, user);

            // Si no tiene padre, es un nodo raíz
            if (referenceParent == null || id.equals(idUser)) {
                rootUsers.add(user);
            } else {
                // Si tiene padre, agregarlo a la lista `child` del padre
                UserDTO parent = userMap.get(referenceParent);
                if (parent != null) {
                    parent.getChild().add(user);
                }
            }
        }
        return rootUsers;
    }
    public ResponseDTO _GetDataAffiliate(int page, int size, String word){
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<User> userAffiliate = userRepository.findAllAffiliate(constantsConfig.getWorkGroupIdAffiliate(), word, pageRequest);
        return ResponseDTO.builder()
                .items(
                    PageableResponseDTO.builder().result(
                        userAffiliate
                            .stream()
                            .map(item -> new UserDTO(item))
                            .collect(Collectors.toList())).isLastPage(userAffiliate.isLast()).build()
                        )
                .build();
    }
}
