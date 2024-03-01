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
    private final EmailMapper emailMapper;
    private final UserUtils userUtils;

    @Transactional
    public EmailDTO createWithUserId(EmailCreateDTO dto, Long userId) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId))
            throw new AccessDeniedException("Access denied to create email by another user");
        if (emailRepository.existsByEmail(dto.getEmail()))
            throw new ResourceAlreadyExistsException(Email.class, Map.of(dto.getEmail(), "email"));
        User userModel = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", userId));
        Email emailModel = emailMapper.map(dto);
        emailModel.setUser(userModel);
        emailRepository.save(emailModel);
        return emailMapper.map(emailModel);
    }

    @Transactional
    public EmailDTO updateByIdWithUserId(Long id, EmailUpdateDTO dto, Long userId) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId))
            throw new AccessDeniedException("Access denied to update email by another user");
        if (emailRepository.existsByEmail(dto.getEmail()))
            throw new ResourceAlreadyExistsException(Email.class, Map.of(dto.getEmail(), "email"));
        Email emailModel = emailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Email.class, "email", dto.getEmail()));
        emailMapper.update(dto, emailModel);
        emailRepository.save(emailModel);
        return emailMapper.map(emailModel);
    }

    @Transactional
    public void deleteByIdWithUserId(Long id, Long userId) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), userId))
            throw new AccessDeniedException("Access denied to delete email by another user");
        if (emailRepository.countByUserId(userId) > 1)
            emailRepository.deleteById(id);
        else
            throw new NoSuchElementException("Emails count must be at least one");
    }
}
