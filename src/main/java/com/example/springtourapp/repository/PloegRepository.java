package com.example.springtourapp.repository;

import org.springframework.data.repository.reactive.*;
import org.springframework.stereotype.*;

import com.example.springtourapp.domain.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Repository
public interface PloegRepository extends ReactiveCrudRepository<Ploeg, Long> {
}
