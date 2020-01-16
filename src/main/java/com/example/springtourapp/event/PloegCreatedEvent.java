package com.example.springtourapp.event;

import org.springframework.context.*;

import com.example.springtourapp.domain.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
public class PloegCreatedEvent extends ApplicationEvent {
	public PloegCreatedEvent(Ploeg source) {
		super(source);
	}
}
