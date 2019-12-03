package com.accenture.refapp;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

	@Autowired
	private Config myConfig;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String greeting() {
		String message = "The config message is: " + this.myConfig.getMessage();
		System.out.println(message);
        return message;
    }

	@RequestMapping(value = "/liveness", method = RequestMethod.GET)
	@ResponseBody
    public ResponseEntity liveness() {
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		Greeting greeting = new Greeting(counter.incrementAndGet(),
							String.format(template, name));
        System.out.printf("Returning greeting with id=\"%s\" and content=\"%s\"\n",
                          greeting.getId(), greeting.getContent());
        return greeting;
	}

}
