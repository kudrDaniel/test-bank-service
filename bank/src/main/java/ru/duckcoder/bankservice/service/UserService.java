package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.duckcoder.bankservice.dto.UserCreateDTO;
import ru.duckcoder.bankservice.dto.UserDTO;
import ru.duckcoder.bankservice.dto.UserUpdateDTO;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.PhoneRepository;
import ru.duckcoder.bankservice.repository.UserRepository;
import ru.duckcoder.bankservice.repository.WalletRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final WalletRepository walletRepository;

    public Page<UserDTO> findAll(Integer page, Integer size) {
        return null;
    }

    public UserDTO findById(Long id) {
        return null;
    }

    public UserDTO create(UserCreateDTO dto) {
        return null;
    }

    public UserDTO update(Long id, UserUpdateDTO dto) {
        return null;
    }

    public void deleteById(Long id) {

    }
}
