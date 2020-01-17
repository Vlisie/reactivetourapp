package com.example.springtourapp;

import java.text.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.web.reactive.server.*;

import com.example.springtourapp.controller.*;
import com.example.springtourapp.handler.*;
import com.example.springtourapp.service.*;

import lombok.extern.log4j.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 15-1-20.
 */

@Log4j2
@ActiveProfiles("default")
@Import({RennerEndpointConfiguration.class,
	RennerHandler.class, RennerService.class})
public class FunctionalRennerEndpointsTest extends AbstractBaseRennerEndpoints {

	@BeforeAll
	static void before() {
		log.info("running default " + RennerController.class.getName() + " tests");
	}

	FunctionalRennerEndpointsTest(@Autowired WebTestClient client) throws ParseException {
		super(client);
	}
}
