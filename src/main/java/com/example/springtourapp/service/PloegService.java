package com.example.springtourapp.service;

import org.springframework.context.*;
import org.springframework.stereotype.*;

import com.example.springtourapp.domain.*;
import com.example.springtourapp.event.*;
import com.example.springtourapp.repository.*;

import lombok.extern.log4j.*;
import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Log4j2
@Service
public class PloegService {
	private final ApplicationEventPublisher m_publisher;
	private final PloegRepository m_ploegRepository;

	PloegService(ApplicationEventPublisher publisher, PloegRepository ploegRepository) {
		this.m_publisher = publisher;
		this.m_ploegRepository = ploegRepository;
	}

	public Flux<Ploeg> all() {
		return this.m_ploegRepository.findAll();
	}

	public Mono<Ploeg> get(Long id) {
		return this.m_ploegRepository.findById(id);
	}

	public Mono<Ploeg> update(Long id, String naam, String ploegleider, String landcode) {
		return this.m_ploegRepository
			.findById(id)
			.map(r -> new Ploeg(r.getId(), naam, ploegleider, landcode))
			.flatMap(this.m_ploegRepository::save);
	}

	public Mono<Ploeg> delete(Long id) {
		return this.m_ploegRepository
			.findById(id)
			.flatMap(r -> this.m_ploegRepository.deleteById(id).thenReturn(r));
	}

	public Mono<Ploeg> create(String naam, String ploegleider, String landcode) {
		return this.m_ploegRepository
			.save(new Ploeg(null,naam, ploegleider, landcode))
			.doOnSuccess(ploeg -> this.m_publisher.publishEvent(new PloegCreatedEvent(ploeg)));
	}

}
