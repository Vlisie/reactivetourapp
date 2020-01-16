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
@RequestMapping(value = "/ploegen", produces = MediaType.APPLICATION_JSON_VALUE)
@org.springframework.context.annotation.Profile("classis")
public class PloegController {

	private final MediaType mediaType = MediaType.APPLICATION_JSON;
	private final PloegService m_ploegService;

	PloegController(PloegService ploegService) {
		this.m_ploegService = ploegService;
	}


	@GetMapping
	Publisher<Ploeg> getAll() {
		return this.m_ploegService.all();
	}


	@GetMapping("/{id}")
	Publisher<Ploeg> getById(@PathVariable("id") Long id) {
		return this.m_ploegService.get(id);
	}


	@PostMapping
	Publisher<ResponseEntity<Ploeg>> create(@RequestBody Ploeg ploeg) {
		return this.m_ploegService
			.create(ploeg.getNaam(), ploeg.getPloegleider(), ploeg.getLandcode())
			.map(r -> ResponseEntity.created(URI.create("/ploegen/" + r.getId()))
				.contentType(mediaType)
				.build());
	}

	@DeleteMapping("/{id}")
	Publisher<Ploeg> deleteById(@PathVariable Long id) {
		return this.m_ploegService.delete(id);
	}

	@PutMapping("/{id}")
	Publisher<ResponseEntity<Ploeg>> updateById(@PathVariable Long id, @RequestBody Ploeg ploeg) {
		return Mono
			.just(ploeg)
			.flatMap(p -> this.m_ploegService.update(id, p.getNaam(), p.getPloegleider(), p.getLandcode()))
			.map(p -> ResponseEntity
				.ok()
				.contentType(this.mediaType)
				.build());
	}
}
