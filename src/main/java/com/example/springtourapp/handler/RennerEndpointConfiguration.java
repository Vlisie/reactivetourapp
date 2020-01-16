package com.example.springtourapp.handler;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springframework.context.annotation.*;
import org.springframework.web.reactive.function.server.*;

import com.example.springtourapp.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Configuration
public class RennerEndpointConfiguration {

		@Bean
		RouterFunction<ServerResponse> routes(RennerHandler handler) {
			return route(i(GET("/renners")), handler::all)
				.andRoute(i(GET("/renners/{id}")), handler::getById)
				.andRoute(i(DELETE("/renners/{id}")), handler::deleteById)
				.andRoute(i(POST("/renners")), handler::create)
				.andRoute(i(PUT("/renners/{id}")), handler::updateById);
		}


		private static RequestPredicate i(RequestPredicate target) {
			return new CaseInsensitiveRequestPredicate(target);
		}
}
