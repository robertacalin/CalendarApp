package server.convert;

import lib.dto.ReminderDto;
import server.model.Reminder;

public class ReminderConvertor {

    public ReminderConvertor() {
    }

    public static Reminder convert(ReminderDto reminderDto) {
        var reminder = new Reminder();

        reminder.setId(reminderDto.getId());
        reminder.setReminderDate(reminderDto.getReminderDate());
        reminder.setReminderHour(reminderDto.getReminderHour());
        reminder.setViewed(reminderDto.isViewed());

        return reminder;
    }

    public static ReminderDto convert(Reminder reminder) {
        ReminderDto reminderDto = new ReminderDto(
                reminder.getReminderDate(),
                reminder.getReminderHour(),
                reminder.getEvent().getId(),
                reminder.isViewed()
        );

        reminderDto.setId(reminder.getId());

        return reminderDto;
    }
}
