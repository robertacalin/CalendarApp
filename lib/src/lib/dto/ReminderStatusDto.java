package lib.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderStatusDto extends ReminderDto {

    public ReminderStatusDto(LocalDate reminderDate, LocalTime reminderHour, int eventId, Status status) {
        super(reminderDate, reminderHour, eventId, false);
        this.status = status;
    }

    public enum Status {
        CONNECTED, DISCONNECTED;
    }

    private final Status status;

    public Status getStatus() {
        return status;
    }
}
