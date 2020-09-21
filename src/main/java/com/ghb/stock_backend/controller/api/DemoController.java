package com.ghb.stock_backend.controller.api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.ghb.stock_backend.util.DateUtils;

//@RestController
public class DemoController {

	// @Autowired
	DateUtils dateUtils;
	private SayService sayService;

	DemoController(DateUtils dateUtils, @Qualifier("dog") SayService sayService) {
		this.dateUtils = dateUtils;
		this.sayService = sayService;
	}

	@GetMapping("/")
	String getToday() {
		return dateUtils.todayString();
	}

	@GetMapping("/say")
	String say() {
		return sayService.say();
	}

}

interface SayService {
	String say();
}

@Component
class Cat implements SayService {

	@Override
	public String say() {
		// TODO Auto-generated method stub
		return "meowwww";
	}

}

@Component
class Dog implements SayService {

	@Override
	public String say() {
		// TODO Auto-generated method stub
		return "boawww";
	}

}