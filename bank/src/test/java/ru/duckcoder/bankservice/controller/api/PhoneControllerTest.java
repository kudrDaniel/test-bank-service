package ru.duckcoder.bankservice.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.duckcoder.bankservice.dto.phone.PhoneCreateDTO;
import ru.duckcoder.bankservice.dto.phone.PhoneUpdateDTO;
import ru.duckcoder.bankservice.model.Email;
import ru.duckcoder.bankservice.model.Phone;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.model.Wallet;
import ru.duckcoder.bankservice.repository.EmailRepository;
import ru.duckcoder.bankservice.repository.PhoneRepository;
import ru.duckcoder.bankservice.repository.UserRepository;
import ru.duckcoder.bankservice.repository.WalletRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private ObjectMapper om;

    private static User testUser0;
    private static User testUser1;
    private static Email testEmail0;
    private static Email testEmail1;
    private static Phone testPhone0;
    private static Phone testPhone1;
    private static Wallet testWallet0;
    private static Wallet testWallet1;
    private static JwtRequestPostProcessor token0;
    private static JwtRequestPostProcessor token1;

    @BeforeEach
    public void beforeEach() {
        String fullName = "Test User ";
        String username = "testUser";
        String password = "01234567";
        LocalDate birthDate = LocalDate.of(1980, 5, 20);
        String email = "testEmail@email.com";
        String phone = "912345678";
        double deposit = 1000.0;

        if (userRepository.existsByUsername(username + 0) || userRepository.existsByUsername(username + 1))
            return;
        testEmail0 = new Email();
        testEmail1 = new Email();
        if (emailRepository.existsByEmail(email + 0) || emailRepository.existsByEmail(email + 1)) {
            return;
        }
        testEmail0.setEmail(0 + email);
        testEmail1.setEmail(1 + email);
        testPhone0 = new Phone();
        testPhone1 = new Phone();
        if (phoneRepository.existsByPhone(phone + 0) || phoneRepository.existsByPhone(phone + 1)) {
            return;
        }
        testPhone0.setPhone(phone + 0);
        testPhone1.setPhone(phone + 1);
        testWallet0 = new Wallet();
        testWallet1 = new Wallet();
        testWallet0.setDeposit(deposit + 0);
        testWallet1.setDeposit(deposit + 1);
        testUser0 = new User();
        testUser1 = new User();
        testUser0.setFullName(fullName + 0);
        testUser1.setFullName(fullName + 1);
        testUser0.setUsername(username + 0);
        testUser1.setUsername(username + 1);
        testUser0.setEmails(List.of(testEmail0));
        testUser1.setEmails(List.of(testEmail1));
        testUser0.setPhones(List.of(testPhone0));
        testUser1.setPhones(List.of(testPhone1));
        testUser0.setWallet(testWallet0);
        testUser1.setWallet(testWallet1);
        testUser0.setBirthDate(birthDate);
        testUser1.setBirthDate(birthDate);
        testUser0.setPassword(passwordEncoder.encode(password));
        testUser1.setPassword(passwordEncoder.encode(password));
        userRepository.save(testUser0);
        userRepository.save(testUser1);
        testEmail0.setUser(testUser0);
        testEmail1.setUser(testUser1);
        emailRepository.save(testEmail0);
        emailRepository.save(testEmail1);
        testPhone0.setUser(testUser0);
        testPhone1.setUser(testUser1);
        phoneRepository.save(testPhone0);
        phoneRepository.save(testPhone1);
        testWallet0.setUser(testUser0);
        testWallet1.setUser(testUser1);
        walletRepository.save(testWallet0);
        walletRepository.save(testWallet1);

        token0 = jwt().jwt(builder -> builder.subject(testUser0.getUsername()));
        token1 = jwt().jwt(builder -> builder.subject(testUser1.getUsername()));
    }

    @AfterEach
    public void afterEach() {
        userRepository.deleteById(testUser1.getId());
        userRepository.deleteById(testUser0.getId());
    }

    @Test
    public void phoneAddTestSuccess() throws Exception {
        PhoneCreateDTO dto = new PhoneCreateDTO();
        dto.setPhone("2223336565");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users/{id}/phones", testUser0.getId())
                .with(token0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        JsonAssertions.assertThatJson(result.getResponse().getContentAsString()).and(
                v -> v.node("phone").asString().isEqualTo(dto.getPhone())
        );

        Optional<Phone> repositoryResult = phoneRepository.findByPhone(dto.getPhone());
        Assertions.assertThat(repositoryResult).isNotEmpty();
        Phone actualModel = repositoryResult.get();
        Assertions.assertThat(actualModel.getPhone()).isEqualTo(dto.getPhone());
        Assertions.assertThat(actualModel.getUser().getId()).isEqualTo(testUser0.getId());
    }

    @Test
    public void phoneAddTestWrongUser() throws Exception {
        PhoneCreateDTO dto = new PhoneCreateDTO();
        dto.setPhone("2223336565");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users/{id}/phones", testUser0.getId())
                .with(token1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void phoneAddTestConflict() throws Exception {
        PhoneCreateDTO dto = new PhoneCreateDTO();
        dto.setPhone("9123456780");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users/{id}/phones", testUser0.getId())
                .with(token0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void phoneUpdateTestSuccess() throws Exception {
        PhoneUpdateDTO dto = new PhoneUpdateDTO();
        dto.setPhone("2223336565");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/users/{userId}/phones/{id}", testUser0.getId(), testPhone0.getId())
                .with(token0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        MvcResult result = mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        JsonAssertions.assertThatJson(result.getResponse().getContentAsString()).and(
                v -> v.node("phone").asString().isEqualTo(dto.getPhone())
        );

        Optional<Phone> repositoryResult = phoneRepository.findByPhone(dto.getPhone());
        Assertions.assertThat(repositoryResult).isNotEmpty();
        Phone actualModel = repositoryResult.get();
        Assertions.assertThat(actualModel.getPhone()).isEqualTo(dto.getPhone());
        Assertions.assertThat(actualModel.getUser().getId()).isEqualTo(testUser0.getId());
    }

    @Test
    public void phoneUpdateTestWrongUser() throws Exception {
        PhoneUpdateDTO dto = new PhoneUpdateDTO();
        dto.setPhone("2223336565");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/users/{userId}/phones/{id}", testUser0.getId(), testPhone0.getId())
                .with(token1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    public void phoneUpdateTestConflict() throws Exception {
        PhoneUpdateDTO dto = new PhoneUpdateDTO();
        dto.setPhone("9123456781");

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/api/users/{userId}/phones/{id}", testUser0.getId(), testPhone0.getId())
                .with(token0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    public void phoneDeleteTestSuccess() throws Exception {
        Phone tmpPhone = new Phone();
        tmpPhone.setPhone("0000000000");
        tmpPhone.setUser(testUser0);
        phoneRepository.save(tmpPhone);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/users/{userId}/phones/{id}", testUser0.getId(), tmpPhone.getId())
                .with(token0);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Assertions.assertThat(phoneRepository.existsByPhone(tmpPhone.getPhone())).isFalse();
    }

    @Test
    public void emailDeleteTestWrongUser() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/users/{userId}/phones/{id}", testUser0.getId(), testPhone1.getId())
                .with(token0);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        Assertions.assertThat(phoneRepository.existsByPhone(testPhone1.getPhone())).isTrue();
    }

    @Test
    public void emailDeleteTestConflict() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/api/users/{userId}/phones/{id}", testUser0.getId(), testPhone0.getId())
                .with(token0);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isConflict());

        Assertions.assertThat(phoneRepository.existsByPhone(testPhone0.getPhone())).isTrue();
    }
}
