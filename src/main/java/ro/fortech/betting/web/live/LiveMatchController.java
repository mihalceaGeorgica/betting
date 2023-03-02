package ro.fortech.betting.web.live;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import ro.fortech.betting.dto.MatchScoreDTO;
import ro.fortech.betting.service.MatchService;
import ro.fortech.betting.service.OddsChangeListenerService;
import ro.fortech.betting.service.events.OddsChangeEvent;

@RestController
@RequestMapping(value = "/api/live")
public class LiveMatchController implements ApplicationListener<OddsChangeEvent> {
	
	@Autowired
	private OddsChangeListenerService oddsListener;
	
	@PostConstruct
	public void init() {
		oddsListener.listenForChanges();
	}

	@Autowired
	private MatchService matchService;

	private final SubscribableChannel subscribableChannel = MessageChannels.publishSubscribe().get();

	@CrossOrigin
	@GetMapping("/match/{matchId}")
	public Flux<ServerSentEvent<MatchScoreDTO>> liveMatchScore(@PathVariable Long matchId) {

		return Flux.interval(Duration.ofSeconds(1))
				.map(sequence -> ServerSentEvent.<MatchScoreDTO>builder().id(String.valueOf(sequence))
						.event("live-odds for match: " + matchId).data(matchService.getLiveScoreForMatch(matchId))
						.build());

	}

	@CrossOrigin
	@GetMapping(path = "/all-matches", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<OddsChangeEvent.Event> getFolderWatch() {
		return Flux.create(sink -> {
			MessageHandler handler = message -> sink
					.next(OddsChangeEvent.class.cast(message.getPayload()).getEvent());
			sink.onCancel(() -> subscribableChannel.unsubscribe(handler));
			subscribableChannel.subscribe(handler);
		}, FluxSink.OverflowStrategy.LATEST);
	}

	@Override
	public void onApplicationEvent(OddsChangeEvent event) {
		subscribableChannel.send(new GenericMessage<>(event));
	}
}
