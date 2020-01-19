package com.example.springtourapp;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.springtourapp.event.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 19-01-20.
 */

@RestController
public class ServerSentEventController {
	private final Flux<RennerCreatedEvent> events;
	private final ObjectMapper objectMapper;

	public ServerSentEventController(RennerCreatedEventPublisher eventPublisher, ObjectMapper objectMapper) {
		this.events = Flux.create(eventPublisher).publish().autoConnect();
		this.objectMapper = objectMapper;
	}

	@GetMapping(path = "/sse/renners", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@CrossOrigin(origins = "http://localhost:3000")
	public Flux<String> renners() {
		return this.events.map(pce -> {
			try {
				return objectMapper.writeValueAsString(pce) + "\n\n";
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});
	}
}
