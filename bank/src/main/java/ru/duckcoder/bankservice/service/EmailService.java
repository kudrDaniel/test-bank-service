package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.duckcoder.bankservice.dto.email.EmailCreateDTO;
import ru.duckcoder.bankservice.dto.email.EmailDTO;
import ru.duckcoder.bankservice.dto.email.EmailUpdateDTO;
import ru.duckcoder.bankservice.exception.ResourceAlreadyExistsException;
import ru.duckcoder.bankservice.exception.ResourceNotFoundException;
import ru.duckcoder.bankservice.mapper.EmailMapper;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.UserRepository;
import ru.duckcoder.bankservice.util.UserUtils;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final EmailMapper mapper;
    private final UserUtils userUtils;

    @Transactional
    public EmailDTO createWithUserId(EmailCreateDTO dto, Long userId) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId))
            throw new AccessDeniedException("Access denied to create email by another user");
        if (emailRepository.existsByEmail(dto.getEmail()))
            throw new ResourceAlreadyExistsException(Email.class, Map.of(dto.getEmail(), "email"));
        User userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", userId));
        Email emailModel = mapper.map(dto);
        emailRepository.save(emailModel);
        emailModel.setUser(userModel);
        return mapper.map(emailModel);
    }

    @Transactional
    public EmailDTO updateByIdWithUserId(Long id, EmailUpdateDTO dto, Long userId) {
        Email emailModel = emailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Email.class, "id", id));
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId)
                || !Objects.equals(emailModel.getUser().getId(), userId))
            throw new AccessDeniedException("Access denied to update email by another user");
        if (emailRepository.existsByEmail(dto.getEmail()))
            throw new ResourceAlreadyExistsException(Email.class, Map.of(dto.getEmail(), "email"));
        mapper.update(dto, emailModel);
        return mapper.map(emailModel);
    }

    @Transactional
    public void deleteByIdWithUserId(Long id, Long userId) {
        Email emailModel = emailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Email.class, "id", id));
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId)
                || !Objects.equals(emailModel.getUser().getId(), userId))
            throw new AccessDeniedException("Access denied to delete email by another user");
        if (emailRepository.countByUserId(userId) > 1)
            emailRepository.deleteById(id);
        else
            throw new NoSuchElementException("Emails count must be at least one");
    }
}
