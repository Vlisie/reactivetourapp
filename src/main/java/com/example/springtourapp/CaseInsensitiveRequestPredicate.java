package com.example.springtourapp;

import java.net.*;

import org.springframework.http.server.*;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.reactive.function.server.support.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */

public class CaseInsensitiveRequestPredicate implements RequestPredicate {

	private final RequestPredicate target;

	public CaseInsensitiveRequestPredicate(RequestPredicate target) {
		this.target = target;
	}

	@Override
	public boolean test(ServerRequest request) {
		return this.target.test(new LowerCaseUriServerRequestWrapper(request));
	}

	@Override
	public String toString() {
		return this.target.toString();
	}
}


class LowerCaseUriServerRequestWrapper extends ServerRequestWrapper {

	LowerCaseUriServerRequestWrapper(ServerRequest delegate) {
		super(delegate);
	}

	@Override
	public URI uri() {
		return URI.create(super.uri().toString().toLowerCase());
	}

	@Override
	public String path() {
		return uri().getRawPath();
	}

	@Override
	public PathContainer pathContainer() {
		return PathContainer.parsePath(path());
	}
}
