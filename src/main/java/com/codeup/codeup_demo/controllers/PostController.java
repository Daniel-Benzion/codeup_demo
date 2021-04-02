package com.codeup.codeup_demo.controllers;

import com.codeup.codeup_demo.models.Post;
import com.codeup.codeup_demo.models.User;
import com.codeup.codeup_demo.repo.PostRepository;
import com.codeup.codeup_demo.repo.UserRepository;
import com.codeup.codeup_demo.services.EmailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

	private final PostRepository postDao;
	private final UserRepository userDao;
	private final EmailService emailService;


	public PostController(PostRepository postDao, UserRepository userDao, EmailService emailService) {
		this.postDao = postDao;
		this.userDao = userDao;
		this.emailService = emailService;
	}

	@GetMapping("/posts")
	public String seeAllPosts(Model model) {
		List<Post> postList = postDao.findAll();
		model.addAttribute("posts", postList);
		return "posts/index";
	}

	@GetMapping("/posts/{id}")
	public String showOnePost(@PathVariable long id, Model model) {
		Post test;
		if (postDao.findById(id).isPresent()){
			test = postDao.getOne(id);
		} else {
			return "redirect:/posts";
		}
		model.addAttribute("post", test);
		User owner = userDao.getOne(1L);
		model.addAttribute("owner", owner);
		model.addAttribute("id", id);
		return "posts/show";
	}

	@GetMapping("/posts/create")
	public String viewCreatePost(Model model) {
		model.addAttribute("post", new Post());
		return "posts/create";
	}

	@PostMapping("/posts/create")
	public String createPost(@ModelAttribute Post post) {
		User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		post.setOwner(owner);
		Post dbPost = postDao.save(post);
		emailService.prepareAndSend(dbPost,"Post created","A post has been created.");
		return "redirect:/posts";
	}

	@GetMapping("/posts/{id}/delete")
	public String showDeletePost(@PathVariable long id, Model model){

		Post post = postDao.getOne(id);

		model.addAttribute("post", post);

		return "posts/delete";
	}

	@PostMapping("/posts/{id}/delete")
	public String deletePost(@PathVariable long id){
		postDao.deleteById(id);
		return "redirect:/posts";
	}

	@GetMapping("/posts/{id}/update")
	public String showUpdatePost(@PathVariable long id, Model model){

		Post post = postDao.getOne(id);

		model.addAttribute("post", post);

		return "posts/update";
	}

	@PostMapping("/posts/{id}/update")
	public String updatePost(@PathVariable long id, @ModelAttribute Post post) {
		User owner = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		post.setOwner(owner);
		postDao.save(post);
		return "redirect:/posts";
	}
}
