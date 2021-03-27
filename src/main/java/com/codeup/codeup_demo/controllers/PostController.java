package com.codeup.codeup_demo.controllers;

import com.codeup.codeup_demo.models.Post;
import com.codeup.codeup_demo.repo.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

	private final PostRepository postDao;

	public PostController(PostRepository postDao) {
		this.postDao = postDao;
	}

	@GetMapping("/posts")
	public String index(Model model) {
		model.addAttribute("posts", postDao.findAll());
		return "posts/index";
	}

	@GetMapping("/posts/{id}")
	@ResponseBody
	public String getPost(@PathVariable String id) {
		return "View individual post. ID: " + id;
	}

	@GetMapping("/posts/create")
	@ResponseBody
	public String viewCreatePost() {
		return "Post creation form.";
	}

	@PostMapping("/posts/create")
	@ResponseBody
	public String createPost() {
		return "Post created.";
	}

	@PostMapping("/posts/{id}/delete")
	public String deletePost(@PathVariable long id){
		postDao.deleteById(id);
		return "redirect:/posts";
	}

	@PostMapping("/posts/{id}/edit")
	public String editPost(
			@PathVariable long id,
			@ModelAttribute Post editedPost
	){

		Post dbPost = postDao.getOne(id);
		dbPost.setTitle(editedPost.getTitle());
		dbPost.setBody(editedPost.getBody());
		postDao.save(dbPost);
		return "redirect:/posts";
	}
}
