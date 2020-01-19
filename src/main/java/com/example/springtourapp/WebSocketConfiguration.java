package com.example.springtourapp;

import java.util.*;
import java.util.concurrent.*;

import org.springframework.context.annotation.*;
import org.springframework.web.reactive.*;
import org.springframework.web.reactive.handler.*;
import org.springframework.web.reactive.socket.*;
import org.springframework.web.reactive.socket.server.support.*;

import com.example.springtourapp.event.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import lombok.extern.log4j.*;
import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Log4j2
@Configuration
class WebSocketConfiguration {

	@Bean
	Executor executor() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean
	HandlerMapping handlerMapping(WebSocketHandler wsh) {
		return new SimpleUrlHandlerMapping() {
			{
				setUrlMap(Collections.singletonMap("/ws/renners", wsh));
				setOrder(10);
			}
		};
	}

	@Bean
	WebSocketHandlerAdapter webSocketHandlerAdapter() {
		return new WebSocketHandlerAdapter();
	}

	@Bean
	WebSocketHandler webSocketHandler(
		ObjectMapper objectMapper,
		RennerCreatedEventPublisher eventPublisher
	) {
		Flux<RennerCreatedEvent> publish = Flux
			.create(eventPublisher)
			.publish().autoConnect();
		return session -> {
			Flux<WebSocketMessage> messageFlux = publish
				.map(evt -> {
					try {
						return objectMapper.writeValueAsString(evt.getSource());
					}
					catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				})
				.map(str -> {
					log.info("sending " + str);
					return session.textMessage(str);
				});
			return session.send(messageFlux);
		};
	}
}
