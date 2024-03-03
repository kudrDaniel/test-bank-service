package ru.duckcoder.bankservice.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.duckcoder.bankservice.dto.user.UserParamsDTO;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.PhoneRepository;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSpecification {
    private final EmailRepository emailRepository;
    private final PhoneRepository phoneRepository;

    public Specification<User> build(UserParamsDTO params) {
        return withFullNameLike(params.getFullName())
                .and(withEmail(params.getEmail()))
                .and(withPhone(params.getPhone()))
                .and(withBirthDateGreaterThan(params.getBirthDate()));
    }

    private Specification<User> withFullNameLike(String fullName) {
        return (r, q, cb) -> fullName == null
                ? cb.conjunction()
                : cb.like(r.get("fullName"), '%' + fullName + '%');
    }

    private Specification<User> withEmail(String email) {
        if (email != null) {
            Optional<Email> optional = emailRepository.findByEmail(email);
            if (optional.isPresent()) {
                return (r, q, cb) -> cb.equal(r.get("id"), optional.get().getUser().getId());
            }
        }
        return (r, q, cb) -> cb.conjunction();
    }

    private Specification<User> withPhone(String phone) {
        if (phone != null) {
            Optional<Phone> optional = phoneRepository.findByPhone(phone);
            if (optional.isPresent()) {
                return (r, q, cb) -> cb.equal(r.get("id"), optional.get().getUser().getId());
            }
        }
        return (r, q, cb) -> cb.conjunction();
    }

    private Specification<User> withBirthDateGreaterThan(LocalDate birthDate) {
        return (r, q, cb) -> birthDate == null
                ? cb.conjunction()
                : cb.greaterThan(r.get("birthDate"), birthDate);
    }
}
