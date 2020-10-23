package lib.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderDto implements Serializable {

    private int id;

    private LocalDate reminderDate;

    private LocalTime reminderHour;

    private int eventId;

    private boolean viewed;

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public ReminderDto(LocalDate reminderDate, LocalTime reminderHour, int eventId, boolean viewed) {
        this.reminderDate = reminderDate;
        this.reminderHour = reminderHour;
        this.eventId = eventId;
        this.viewed = viewed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(LocalDate reminderDate) {
        this.reminderDate = reminderDate;
    }

    public LocalTime getReminderHour() {
        return reminderHour;
    }

    public void setReminderHour(LocalTime reminderHour) {
        this.reminderHour = reminderHour;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {
        return "Reminder: " +
                id +
                reminderDate +
                ", " + reminderHour;
    }
}
