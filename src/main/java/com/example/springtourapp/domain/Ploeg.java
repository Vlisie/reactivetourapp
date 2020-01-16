package com.example.springtourapp.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;

import lombok.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Table("Ploegen")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ploeg {
	@Id
	private Long id;
	@NonNull
	private String naam;
	private String ploegleider;
	private String landcode;

	public Ploeg(Long id) {
		this.id = id;
	}
}
