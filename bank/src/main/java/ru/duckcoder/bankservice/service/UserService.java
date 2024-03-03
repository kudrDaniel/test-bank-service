package ru.duckcoder.bankservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.duckcoder.bankservice.dto.user.UserCreateDTO;
import ru.duckcoder.bankservice.dto.user.UserDTO;
import ru.duckcoder.bankservice.dto.user.UserParamsDTO;
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
import ru.duckcoder.bankservice.specification.UserSpecification;
import ru.duckcoder.bankservice.util.UserUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;
    private final UserMapper mapper;
    private final UserUtils userUtils;
    private final UserSpecification userSpecification;

    public Page<UserDTO> findAll(UserParamsDTO params, Integer page, Integer size, String[] orderBy, String direction) {
        if (page < 1)
            page = 1;
        if (size < 1)
            size = 10;
        Sort sort = buildSort(direction, orderBy);
        if (params == null) {
            params = new UserParamsDTO();
            params.setEmail(null);
            params.setPhone(null);
            params.setBirthDate(null);
            params.setFullName(null);
        }
        Specification<User> specification = userSpecification.build(params);
        Page<User> models = userRepository.findAll(specification, PageRequest.of(page - 1, size, sort));
        return models.map(mapper::map);
    }

    public UserDTO findById(Long id) {
        User model = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "id", id));
        return mapper.map(model);
    }

    @Transactional
    public UserDTO create(UserCreateDTO dto) {
        Map<String, String> violations = new HashMap<>();
        if (userRepository.existsByUsername(dto.getUsername()))
            violations.put(dto.getUsername(), "username");
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
        email.setUser(model);
        phone.setUser(model);
        wallet.setUser(model);
        userRepository.save(model);
        return mapper.map(model);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!Objects.equals(userUtils.getCurrentUser().getId(), id))
            throw new AccessDeniedException("Access denied to delete another user");
        userRepository.deleteById(id);
    }

    private Sort buildSort(String direction, String... properties) {
        Sort sort;
        if (direction != null)
            if (properties == null)
                sort = Sort.by(Sort.Direction.fromString(direction), "id");
            else
                sort = Sort.by(Sort.Direction.fromString(direction), properties);
        else if (properties == null)
            sort = Sort.by(Sort.Direction.ASC, "id");
        else
            sort = Sort.by(Sort.Direction.ASC, properties);
        return sort;
    }
}
