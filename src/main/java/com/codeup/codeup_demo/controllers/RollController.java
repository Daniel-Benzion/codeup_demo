package com.codeup.codeup_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Random;

@Controller
public class RollController {

	@GetMapping("/roll-dice")
	public String index() {
		return "rolldice";
	}

	@GetMapping("roll-dice/{guess}")
	public String result(@PathVariable int guess, Model model) {
		int[] rolls = new int[6];
		for (int i = 0; i < rolls.length; i++) {
			Random random = new Random();
			int roll = random.nextInt(7 - 1) + 1;
			rolls[i] = roll;
		}
		int correct = 0;
		for (int roll : rolls) {
			if (guess == roll) correct++;
		}


		model.addAttribute("rolls", rolls);
		model.addAttribute("guess", guess);
		model.addAttribute("correct", correct);

		return "rollresult";
	}


}
