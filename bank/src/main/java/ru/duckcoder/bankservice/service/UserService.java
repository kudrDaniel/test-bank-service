package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.duckcoder.bankservice.dto.UserCreateDTO;
import ru.duckcoder.bankservice.dto.UserDTO;
import ru.duckcoder.bankservice.dto.UserTransferDTO;
import ru.duckcoder.bankservice.dto.UserUpdateDTO;
import ru.duckcoder.bankservice.exception.ResourceAlreadyExistsException;
import ru.duckcoder.bankservice.exception.ResourceNotFoundException;
import ru.duckcoder.bankservice.mapper.UserMapper;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.model.Wallet;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.PhoneRepository;
import ru.duckcoder.bankservice.repository.UserRepository;
import ru.duckcoder.bankservice.util.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final UserMapper mapper;
    private final UserUtils userUtils;

    public List<UserDTO> findAll(Integer page, Integer size) {
        Page<User> models = userRepository.findAll(PageRequest.of(page, size));
        return models.stream()
                .map(mapper::map)
                .toList();
    }

    public UserDTO findById(Long id) {
        User model = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", id));
        return mapper.map(model);
    }

    @Transactional
    public UserDTO create(UserCreateDTO dto){
        Map<String, String> violations = new HashMap<>();
        if (emailRepository.existsByEmail(dto.getEmail()))
            violations.put(dto.getEmail(), "email");
        if (phoneRepository.existsByPhone(dto.getPhone()))
            violations.put(dto.getPhone(), "phone");
        if (!violations.isEmpty())
            throw new ResourceAlreadyExistsException(User.class, violations);
        User model = mapper.map(dto);
        Email email = new Email();
        email.setEmail(dto.getEmail());
        model.setEmails(List.of(email));
        Phone phone = new Phone();
        phone.setPhone(dto.getPhone());
        model.setPhones(List.of(phone));
        Wallet wallet = new Wallet();
        wallet.setDeposit(dto.getDeposit());
        model.setWallet(wallet);
        model = userRepository.save(model);
        return mapper.map(model);
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), id))
            throw new AccessDeniedException("Access denied to update another user");
        Map<String, String> violations = new HashMap<>();
        if (dto.getEmails() != null) {
            if (dto.getEmails().isPresent()) {
                dto.getEmails().get().stream()
                        .filter(email -> emailRepository.existsByEmailAndIdNot(email.getEmail(), id))
                        .forEach(email -> violations.put(email.getEmail(), "email"));
            } else
                throw new NoSuchElementException("At least one email must be declared");
        }
        if (dto.getPhones() != null) {
            if (dto.getPhones().isPresent()) {
                dto.getPhones().get().stream()
                        .filter(phone -> emailRepository.existsByEmailAndIdNot(phone.getPhone(), id))
                        .forEach(phone -> violations.put(phone.getPhone(), "phone"));
            } else
                throw new NoSuchElementException("At least one phone number must be declared");
        }
        if (!violations.isEmpty()) {
            throw new ResourceAlreadyExistsException(User.class, violations);
        }
        User model = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", id));
        mapper.update(dto, model);
        model = userRepository.save(model);
        return mapper.map(model);
    }

    @Transactional
    public UserDTO transfer(Long id, UserTransferDTO dto) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), id))
            throw new AccessDeniedException("Access denied to update another user");
        User senderModel = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", id));
        User receiverModel = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", dto.getReceiverId()));
        if (senderModel.getWallet().removeFromDeposit(dto.getCount())) {
            receiverModel.getWallet().addToDeposit(dto.getCount());
            senderModel = userRepository.save(senderModel);
            userRepository.save(receiverModel);
        } else {
            throw new AccessDeniedException("Balance cannot be");
        }
        return mapper.map(senderModel);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), id))
            throw new AccessDeniedException("Access denied to update another user");
        userRepository.deleteById(id);
    }
}
