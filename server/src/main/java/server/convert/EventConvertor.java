package server.convert;

import lib.dto.EventDto;
import server.model.Event;

import java.util.Collections;

public class EventConvertor {

    public EventConvertor() {
    }

    public static Event convert(EventDto eventDto) {
        var event = new Event();

        event.setId(eventDto.getId());
        event.setName(eventDto.getName());
        event.setEventDate(eventDto.getEventDate());
        event.setEventHour(eventDto.getEventHour());
        event.setReminders(Collections.emptySet());

        return event;
    }

    public static EventDto convert(Event event) {
        EventDto eventDto = new EventDto(
                event.getName(),
                event.getEventDate(),
                event.getEventHour(),
                event.getAccount().getId()
        );

        eventDto.setId(event.getId());
        eventDto.setRemindersId(Collections.emptySet());

        return eventDto;
    }
}
