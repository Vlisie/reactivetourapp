package com.example.springtourapp.repository;

import org.springframework.data.r2dbc.repository.*;
import org.springframework.data.repository.reactive.*;
import org.springframework.stereotype.*;

import com.example.springtourapp.domain.*;

import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Repository
public interface RennerRepository extends ReactiveCrudRepository<Renner, Long> {
	@Query("SELECT * FROM renners WHERE ploeg = :ploeg and gestart = :gestart")
	Flux<Renner> findAllByPloegAndGestart(Long ploeg, Boolean gestart);
}
