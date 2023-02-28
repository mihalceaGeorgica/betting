package ro.fortech.betting.service.events;

import java.nio.file.WatchEvent;

import org.springframework.context.ApplicationEvent;

import ro.fortech.betting.dto.MatchScoreDTO;

public class OddsChangeEvent extends ApplicationEvent {

 
	private static final long serialVersionUID = -4915744719897004267L;
	private final Event event;

	public OddsChangeEvent(Object source, String data) {
		super(source);
		this.event = new Event(data);
	}

	public Event getEvent() {
		return event;
	}

	public static class Event {
		private final String data;

		public Event(String data) {
			this.data = data;
		}

		public String getData() {
			return data;
		}

		@Override
		public String toString() {
			return this.data;
		}
	}
}
