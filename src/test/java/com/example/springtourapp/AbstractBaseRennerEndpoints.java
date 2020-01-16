package com.example.springtourapp;

import static org.mockito.Mockito.*;

import java.text.*;
import java.time.*;

import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.reactive.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.reactive.server.*;

import com.example.springtourapp.domain.*;
import com.example.springtourapp.repository.*;

import lombok.extern.log4j.*;
import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 15-1-20.
 */
@Log4j2
@WebFluxTest
public abstract class AbstractBaseRennerEndpoints {
	private final WebTestClient client;

	@MockBean
	private RennerRepository repository;

	//SimpleDateFormat m_sdf = new SimpleDateFormat("yyy-MM-dd");
	private LocalDate m_date = LocalDate.of(2020,1,1);


	public AbstractBaseRennerEndpoints(WebTestClient client) throws ParseException {
		this.client = client;
	}

	@Test
	public void getAll() {

		log.info("running  " + this.getClass().getName());

		Mockito
			.when(this.repository.findAll())
			.thenReturn(Flux.just(new Renner(1L, "a", "1" , m_date ), new Renner(2L , "b", "2" , m_date)));

		this.client
			.get()
			.uri("/renners")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("$.[0].id").isEqualTo(1L)
			.jsonPath("$.[0].voornaam").isEqualTo("a")
			.jsonPath("$.[0].achternaam").isEqualTo("1")
			.jsonPath("$.[0].geboortedatum").isEqualTo(m_date.toString())
			.jsonPath("$.[1].id").isEqualTo(2L)
			.jsonPath("$.[1].voornaam").isEqualTo("b")
			.jsonPath("$.[1].achternaam").isEqualTo("2")
			.jsonPath("$.[1].geboortedatum").isEqualTo(m_date.toString());
	}

	@Test
	public void save() {
		Renner data = new Renner(1L, "abc", "def" , m_date);
		when(this.repository.save(Mockito.any(Renner.class)))
			.thenReturn(Mono.just(data));
		MediaType jsonUtf8 = MediaType.APPLICATION_JSON;
		this
			.client
			.post()
			.uri("/renners")
			.contentType(jsonUtf8)
			.body(Mono.just(data), Renner.class)
			.exchange()
			.expectStatus().isCreated()
			.expectHeader().contentType(jsonUtf8);
	}

	@Test
	public void delete() {
		Renner data = new Renner(1L, "abcgfg", "defhfgh" , m_date);
		when(this.repository.findById(data.getId()))
			.thenReturn(Mono.just(data));
		when(this.repository.deleteById(data.getId()))
			.thenReturn(Mono.empty());
		this
			.client
			.delete()
			.uri("/renners/" + data.getId())
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	public void update() {
		Renner data = new Renner(1L, "agdfgbc", "gfghdef" , m_date);

		when(this.repository.findById(data.getId()))
			.thenReturn(Mono.just(data));

		when(this.repository.save(data))
			.thenReturn(Mono.just(data));

		this
			.client
			.put()
			.uri("/renners/" + data.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.body(Mono.just(data), Renner.class)
			.exchange()
			.expectStatus().isOk();
	}

	@Test
	public void getById() {

		Renner data = new Renner(1L, "agdhfbc", "dekjf" , m_date);

		when(this.repository.findById(data.getId()))
			.thenReturn(Mono.just(data));

		this.client
			.get()
			.uri("/renners/" + data.getId())
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectHeader().contentType(MediaType.APPLICATION_JSON)
			.expectBody()
			.jsonPath("$.id").isEqualTo(data.getId())
			.jsonPath("$.voornaam").isEqualTo(data.getVoornaam())
			.jsonPath("$.achternaam").isEqualTo(data.getAchternaam())
			.jsonPath("$.geboortedatum").isEqualTo(data.getGeboortedatum().toString());
	}
}
