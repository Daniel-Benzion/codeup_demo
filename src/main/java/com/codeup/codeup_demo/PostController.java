package com.codeup.codeup_demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostController {

	@GetMapping("/posts")
	@ResponseBody
	public String getPosts() {
		return "View all posts";
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
}
