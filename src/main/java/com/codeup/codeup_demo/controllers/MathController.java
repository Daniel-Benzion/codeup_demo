package com.codeup.codeup_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MathController {

	@GetMapping("/add/{num1}/and/{num2}")
	@ResponseBody
	public int addNumbers(@PathVariable int num1, @PathVariable int num2) {
		return num1 + num2;
	}

	@GetMapping("/subtract/{num1}/from/{num2}")
	@ResponseBody
	public int subtractNumbers(@PathVariable int num1, @PathVariable int num2) {
		return num2 - num1;
	}

	@GetMapping("/multiply/{num1}/and/{num2}")
	@ResponseBody
	public int multiplyNumbers(@PathVariable int num1, @PathVariable int num2) {
		return num1 * num2;
	}

	@GetMapping("/divide/{num1}/by/{num2}")
	@ResponseBody
	public double divideNumbers(@PathVariable int num1, @PathVariable int num2) {
		return (num1 * 1.0) / num2;
	}
}
