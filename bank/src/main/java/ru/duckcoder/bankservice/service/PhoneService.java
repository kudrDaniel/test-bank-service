package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.duckcoder.bankservice.dto.phone.PhoneCreateDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneUpdateDTO;
import ru.duckcoder.bankservice.exception.ResourceAlreadyExistsException;
import ru.duckcoder.bankservice.exception.ResourceNotFoundException;
import ru.duckcoder.bankservice.mapper.PhoneMapper;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.repository.PhoneRepository;
import ru.duckcoder.bankservice.repository.UserRepository;
import ru.duckcoder.bankservice.util.UserUtils;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhoneService {
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final PhoneMapper mapper;
    private final UserUtils userUtils;

    @Transactional
    public PhoneDTO createWithUserId(PhoneCreateDTO dto, Long userId) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId))
            throw new AccessDeniedException("Access denied to create phone by another user");
        if (phoneRepository.existsByPhone(dto.getPhone()))
            throw new ResourceAlreadyExistsException(Phone.class, Map.of(dto.getPhone(), "phone"));
        User userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", userId));
        Phone phoneModel = mapper.map(dto);
        phoneModel.setUser(userModel);
        phoneRepository.save(phoneModel);
        return mapper.map(phoneModel);
    }

    @Transactional
    public PhoneDTO updateByIdWithUserId(Long id, PhoneUpdateDTO dto, Long userId) {
        Phone phoneModel = phoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Phone.class, "id", id));
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId)
                || !Objects.equals(phoneModel.getUser().getId(), userId))
            throw new AccessDeniedException("Access denied to update phone by another user");
        if (phoneRepository.existsByPhone(dto.getPhone()))
            throw new ResourceAlreadyExistsException(Phone.class, Map.of(dto.getPhone(), "phone"));
        mapper.update(dto, phoneModel);
        return mapper.map(phoneModel);
    }

    @Transactional
    public void deleteByIdWithUserId(Long id, Long userId) {
        Phone phoneModel = phoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Phone.class, "id", id));
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId)
                || !Objects.equals(phoneModel.getUser().getId(), userId))
            throw new AccessDeniedException("Access denied to update phone by another user");
        if (phoneRepository.countByUserId(userId) > 1)
            phoneRepository.deleteById(id);
        else
            throw new NoSuchElementException("Phones count must be at least one");
    }
}
