package com.codeup.codeup_demo;

import com.codeup.codeup_demo.models.Post;
import com.codeup.codeup_demo.models.User;
import com.codeup.codeup_demo.repo.PostRepository;
import com.codeup.codeup_demo.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CodeupDemoApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationsTests {

	private HttpSession httpSession;

	@Autowired
	private MockMvc mvc;

	@Autowired
	UserRepository userDao;

	@Autowired
	PostRepository postsDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Before
	public void setup() throws Exception {

		User testUser = userDao.findByUsername("testUser");

		// Creates the test user if not exists
		if(testUser == null){
			User newUser = new User();
			newUser.setUsername("testUser");
			newUser.setPassword(passwordEncoder.encode("pass"));
			newUser.setEmail("testUser@codeup.com");
			testUser = userDao.save(newUser);
		}

		// Throws a Post request to /login and expect a redirection to the posts index page after being logged in
		httpSession = this.mvc.perform(post("/login").with(csrf())
				.param("username", "testUser")
				.param("password", "pass"))
				.andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(redirectedUrl("/posts"))
				.andReturn()
				.getRequest()
				.getSession();
	}

	@Test
	public void contextLoads() {
		assertNotNull(mvc);
	}

	@Test
	public void testIfUserSessionIsActive() throws Exception {
		assertNotNull(httpSession);
	}

	@Test
	public void testCreatePost() throws Exception {
		this.mvc.perform(
				post("/posts/create").with(csrf())
						.session((MockHttpSession) httpSession)
						.param("title", "Test Title")
						.param("body", "Test Body"))
				.andExpect(status().is3xxRedirection());
	}

	@Test
	public void testShowPost() throws Exception {

		Post existingPost = postsDao.findAll().get(0);

		this.mvc.perform(get("/posts/" + existingPost.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(existingPost.getBody())));
	}

	@Test
	public void testPostsIndex() throws Exception {

		Post existingPost = postsDao.findAll().get(0);

		this.mvc.perform(get("/posts"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString(existingPost.getTitle())));
	}

	@Test
	public void testUpdatePost() throws Exception {

		Post existingPost = postsDao.findAll().get(0);

		this.mvc.perform(
				post("/posts/" + existingPost.getId() + "/update").with(csrf())
						.session((MockHttpSession) httpSession)
						.param("title", "New Title")
						.param("body", "New Body"))
				.andExpect(status().is3xxRedirection());

		this.mvc.perform(get("/posts/" + existingPost.getId()))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("New Title")))
				.andExpect(content().string(containsString("New Body")));
	}

	@Test
	public void testDeletePost() throws Exception {
		this.mvc.perform(
				post("/posts/create").with(csrf())
						.session((MockHttpSession) httpSession)
						.param("title", "Delete")
						.param("body", "delete this"))
				.andExpect(status().is3xxRedirection());

		Post existingPost = postsDao.findByTitle("Delete");

		this.mvc.perform(
				post("/posts/" + existingPost.getId() + "/delete").with(csrf())
						.session((MockHttpSession) httpSession))
				.andExpect(status().is3xxRedirection());
	}
}
