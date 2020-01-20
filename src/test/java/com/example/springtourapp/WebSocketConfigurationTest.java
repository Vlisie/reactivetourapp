package com.example.springtourapp;

import java.net.*;
import java.time.*;
import java.util.concurrent.atomic.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.reactivestreams.*;
import org.springframework.boot.test.context.*;
import org.springframework.web.reactive.function.*;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.reactive.socket.*;
import org.springframework.web.reactive.socket.client.*;

import com.example.springtourapp.domain.*;

import lombok.extern.log4j.*;
import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 16-1-20.
 */
@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WebSocketConfigurationTest {


	private final WebSocketClient socketClient = new ReactorNettyWebSocketClient();


	private final WebClient webClient = WebClient.builder().build();


	private Renner generateRandomRenner() {
		return new Renner("tfs", "dft", LocalDate.now(), 1L);
	}

	@Test
	public void testNotificationsOnUpdates() throws Exception {
		int count = 10;
		AtomicLong counter = new AtomicLong();
		URI uri = URI.create("ws://localhost:8080/ws/renners");

		socketClient.execute(uri, (WebSocketSession session) -> {
			Mono<WebSocketMessage> out = Mono.just(session.textMessage("test"));
			Flux<String> in = session
				.receive()
				.map(WebSocketMessage::getPayloadAsText);
			return session
				.send(out)
				.thenMany(in)
				.doOnNext(str -> counter.incrementAndGet())
				.then();
		}).subscribe();

		Flux
			.<Renner>generate(sink -> sink.next(generateRandomRenner()))
			.take(count)
			.flatMap(this::write)
			.blockLast();
		Thread.sleep(1000);
		Assertions.assertThat(counter.get()).isEqualTo(count);
	}

	private Publisher<Renner> write(Renner p) {
		return
			this.webClient
				.post()
				.uri("http://localhost:8080/renners")
				.body(BodyInserters.fromObject(p))
				.retrieve()
				.bodyToMono(String.class)
				.thenReturn(p);
	}
}
