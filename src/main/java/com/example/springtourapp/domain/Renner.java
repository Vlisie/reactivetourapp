package com.example.springtourapp.domain;

import java.time.*;

import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.*;
import org.springframework.format.annotation.*;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;

import lombok.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Table("Renners")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Renner {
	@Id
	private Long id;
	@NonNull
	private String voornaam;
	@NonNull
	private String achternaam;
	@NonNull
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate geboortedatum;
	private Integer rugnummer;
	private Boolean gestart;
	private String landcode;
	private Long ploeg;
	private Boolean afgestapt;

	public Renner(@NonNull Long id, @NonNull String voornaam, @NonNull String achternaam, @NonNull LocalDate geboortedatum) {
		this.id = id;
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.geboortedatum = geboortedatum;
	}

	public Renner(String voornaam, String achternaam, LocalDate geboortedatum, Long ploeg) {
		this.voornaam = voornaam;
		this.achternaam = achternaam;
		this.geboortedatum = geboortedatum;
		this.ploeg = ploeg;
	}
}
