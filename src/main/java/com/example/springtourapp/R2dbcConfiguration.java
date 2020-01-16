package com.example.springtourapp;

import org.springframework.context.annotation.*;
import org.springframework.data.r2dbc.config.*;
import org.springframework.data.r2dbc.repository.config.*;

import io.r2dbc.postgresql.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 13-1-20.
 */
@Configuration
@EnableR2dbcRepositories
public class R2dbcConfiguration extends AbstractR2dbcConfiguration {
	@Override
	@Bean
	public PostgresqlConnectionFactory connectionFactory() {
		return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
			.host("localhost")
			.database("reactivetour")
			.username("postgres")
			.password("tes").build());
	}
}
