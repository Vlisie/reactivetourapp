package com.example.springtourapp;

import java.util.concurrent.*;
import java.util.function.*;

import org.springframework.context.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import com.example.springtourapp.event.*;

import reactor.core.publisher.*;

/**
 * @author <a href="mailto:tes.van.der.vlist@itris.nl">Tes van der Vlist</a>
 * Created on 14-1-20.
 */
@Component
class RennerCreatedEventPublisher implements
	ApplicationListener<RennerCreatedEvent>,
	Consumer<FluxSink<RennerCreatedEvent>> {

	private final Executor executor;
	private final BlockingQueue<RennerCreatedEvent> queue =
		new LinkedBlockingQueue<>();

	RennerCreatedEventPublisher(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void onApplicationEvent(RennerCreatedEvent event) {
		this.queue.offer(event);
	}

	@Override
	public void accept(FluxSink<RennerCreatedEvent> sink) {
		this.executor.execute(() -> {
			while (true)
				try {
					RennerCreatedEvent event = queue.take();
					sink.next(event);
				}
				catch (InterruptedException e) {
					ReflectionUtils.rethrowRuntimeException(e);
				}
		});
	}
}
