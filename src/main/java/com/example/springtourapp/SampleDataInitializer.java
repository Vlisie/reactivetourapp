/*
package com.example.springtourapp;

import java.time.*;

import org.springframework.boot.context.event.*;
import org.springframework.context.*;
import org.springframework.stereotype.*;

import com.example.springtourapp.domain.*;
import com.example.springtourapp.repository.*;

import lombok.extern.log4j.*;
import reactor.core.publisher.*;

*/
/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 16-1-20.
 *//*


@Log4j2
@Component
@org.springframework.context.annotation.Profile("demo")
class SampleDataInitializer
	implements ApplicationListener<ApplicationReadyEvent> {

	private final RennerRepository m_rennerRepository;

	public SampleDataInitializer(RennerRepository rennerRepository) {
		this.m_rennerRepository = rennerRepository;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		m_rennerRepository
			.deleteAll()
			.thenMany(
				Flux
					.just("A", "B", "C", "D")
					.map(name -> new Renner("tes", "vlist" , LocalDate.now(), 1L))
					.flatMap(m_rennerRepository::save)
			)
			.thenMany(m_rennerRepository.findAll())
			.subscribe(profile -> log.info("saving " + profile.toString()));
	}
}
*/
