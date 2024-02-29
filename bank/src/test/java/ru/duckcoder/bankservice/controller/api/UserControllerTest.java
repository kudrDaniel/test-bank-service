package ru.duckcoder.bankservice.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.duckcoder.bankservice.model.User;
import ru.duckcoder.bankservice.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper om;

	private JwtRequestPostProcessor token;

	private User testUser;

	@BeforeEach
	public void beforeEach() {
		testUser = new User();
		userRepository.save(testUser);
		token = SecurityMockMvcRequestPostProcessors.jwt().jwt(builder -> builder.subject(testUser.getEmail().get(0)));
	}

	@Test
	public void userCreateTest() throws Exception {
		User newUser = new User();

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(newUser));

		MvcResult result = mockMvc.perform(request)
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andReturn();

		String resultContent = result.getResponse().getContentAsString();

		JsonAssertions.assertThatJson(resultContent).and(
				v -> v.node("firstName").isEqualTo(newUser.getFullName()),
				v -> v.node("birthDate").isEqualTo(newUser.getBirthDate()),
				v -> v.node("email").isNotNull(),
				v -> v.node("phoneNumber").isNotNull()
		);
	}

	@Test
	public void userIndexTest() throws Exception {

	}

	@Test
	public void userShowTest() throws Exception {

	}

	@Test
	public void userUpdateTest() throws Exception {

	}

	@Test
	public void userDeleteTest() throws Exception {

	}

	@Test
	public void userTransactionTest() throws Exception {

	}
}
