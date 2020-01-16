package com.example.springtourapp.endpoint;

import java.net.*;

import org.reactivestreams.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.springtourapp.domain.*;
import com.example.springtourapp.service.*;

import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@RestController
@RequestMapping(value = "/renners", produces = MediaType.APPLICATION_JSON_VALUE)
//@org.springframework.context.annotation.Profile("classis")
public class RennerController {

	private final MediaType mediaType = MediaType.APPLICATION_JSON;
	private final RennerService m_rennerService;

	RennerController(RennerService rennerService) {
		this.m_rennerService = rennerService;
	}


	@GetMapping
	//@RequestMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	Publisher<Renner> getAll() {
		return this.m_rennerService.all();
	}


	@GetMapping("/{id}")
	Publisher<Renner> getById(@PathVariable("id") Long id) {
		return this.m_rennerService.get(id);
	}


	@PostMapping
	Publisher<ResponseEntity<Renner>> create(@RequestBody Renner renner) {
		return this.m_rennerService
			.create(renner.getVoornaam(), renner.getAchternaam(), renner.getGeboortedatum(), renner.getRugnummer(), renner.getGestart(), renner.getLandcode(), renner.getPloeg(), renner.getAfgestapt())
			.map(r -> ResponseEntity.created(URI.create("/renners/" + r.getId()))
				.contentType(mediaType)
				.build());
	}

	@DeleteMapping("/{id}")
	Publisher<Renner> deleteById(@PathVariable Long id) {
		return this.m_rennerService.delete(id);
	}

	@PutMapping("/{id}")
	Publisher<ResponseEntity<Renner>> updateById(@PathVariable Long id, @RequestBody Renner renner) {
		return Mono
			.just(renner)
			.flatMap(r -> this.m_rennerService.update(id, r.getVoornaam(), r.getAchternaam(), r.getGeboortedatum(), r.getRugnummer(), r.getGestart(), r.getLandcode(), r.getPloeg(), r.getAfgestapt()))
			.map(p -> ResponseEntity
				.ok()
				.contentType(this.mediaType)
				.build());
	}
}
