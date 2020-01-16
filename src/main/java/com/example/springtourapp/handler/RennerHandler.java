package com.example.springtourapp.handler;

import java.net.*;

import org.reactivestreams.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.reactive.function.server.*;

import com.example.springtourapp.domain.*;
import com.example.springtourapp.service.*;

import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Component
public class RennerHandler {
	private final RennerService m_rennerService;

	RennerHandler(RennerService rennerService) {
		this.m_rennerService = rennerService;
	}


	Mono<ServerResponse> getById(ServerRequest s) {
		return defaultReadResponse(this.m_rennerService.get(id(s)));
	}

	Mono<ServerResponse> all(ServerRequest s) {
		return defaultReadResponse(this.m_rennerService.all());
	}

	Mono<ServerResponse> deleteById(ServerRequest s) {
		return defaultReadResponse(this.m_rennerService.delete(id(s)));
	}

	Mono<ServerResponse> updateById(ServerRequest s) {
		Flux<Renner> id = s.bodyToFlux(Renner.class)
			.flatMap(r -> this.m_rennerService.update(id(s), r.getVoornaam(), r.getAchternaam(), r.getGeboortedatum(), r.getRugnummer(), r.getGestart(), r.getLandcode(), r.getPloeg(), r.getAfgestapt()));
		return defaultReadResponse(id);
	}

	Mono<ServerResponse> create(ServerRequest request) {
		Flux<Renner> flux = request
			.bodyToFlux(Renner.class)
			.flatMap(toWrite -> this.m_rennerService
				.create(toWrite.getVoornaam(), toWrite.getAchternaam(), toWrite.getGeboortedatum(), toWrite.getRugnummer(), toWrite.getGestart(), toWrite.getLandcode(), toWrite.getPloeg(), toWrite.getAfgestapt()));
		return defaultWriteResponse(flux);
	}


	private static Mono<ServerResponse> defaultWriteResponse(Publisher<Renner> renners) {
		return Mono
			.from(renners)
			.flatMap(r -> ServerResponse
				.created(URI.create("/renners/" + r.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.build()
			);
	}


	private static Mono<ServerResponse> defaultReadResponse(Publisher<Renner> renners) {
		return ServerResponse
			.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(renners, Renner.class);
	}

	private static Long id(ServerRequest r) {
		return Long.valueOf(r.pathVariable("id"));
	}
}
