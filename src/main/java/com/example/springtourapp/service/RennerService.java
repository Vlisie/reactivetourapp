package com.example.springtourapp.service;

import java.time.*;

import org.springframework.context.*;
import org.springframework.stereotype.*;

import com.example.springtourapp.domain.*;
import com.example.springtourapp.event.*;
import com.example.springtourapp.repository.*;

import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Service
public class RennerService {
	private final ApplicationEventPublisher m_publisher;
	private final RennerRepository m_rennerRepository;

	RennerService(ApplicationEventPublisher publisher, RennerRepository rennerRepository) {
		this.m_publisher = publisher;
		this.m_rennerRepository = rennerRepository;
	}

	public Flux<Renner> all() {
		return this.m_rennerRepository.findAll();
	}

	public Flux<Renner> allGestartByPloeg(Long ploeg, Boolean gestart) {return this.m_rennerRepository.findAllByPloegAndGestart(ploeg, gestart);}

	public Mono<Renner> get(Long id) {
		return this.m_rennerRepository.findById(id);
	}

	public Mono<Renner> update(Long id, String voornaam, String achternaam, LocalDate geboortedatum, Integer rugnummer, Boolean gestart,String landcode, Long ploeg, Boolean afgestapt) {
		return this.m_rennerRepository
			.findById(id)
			.map(r -> new Renner(r.getId(), voornaam, achternaam, geboortedatum, rugnummer, gestart, landcode, ploeg, afgestapt))
			.flatMap(this.m_rennerRepository::save);
	}

	public Mono<Renner> delete(Long id) {
		return this.m_rennerRepository
			.findById(id)
			.flatMap(r -> this.m_rennerRepository.deleteById(id).thenReturn(r));
	}

	public Mono<Renner> create(String voornaam, String achternaam, LocalDate geboortedatum, Integer rugnummer, Boolean gestart, String landcode, Long ploeg, Boolean afgestapt) {
		return this.m_rennerRepository
			.save(new Renner(null, voornaam, achternaam, geboortedatum, rugnummer, gestart, landcode, ploeg, afgestapt))
			.doOnSuccess(renner -> this.m_publisher.publishEvent(new RennerCreatedEvent(renner)));
	}
}
