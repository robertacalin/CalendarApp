package lib.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class EventDto implements Serializable {

    private int id;

    private String name;

    private LocalDate eventDate;

    private LocalTime eventHour;

    private int accountId;

    private Set<Integer> remindersId = new HashSet<>();


    public EventDto(String name, LocalDate eventDate, LocalTime eventHour, int accountId) {
        this.name = name;
        this.eventDate = eventDate;
        this.eventHour = eventHour;
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventHour() {
        return eventHour;
    }

    public void setEventHour(LocalTime eventHour) {
        this.eventHour = eventHour;
    }

    public Set<Integer> getRemindersId() {
        return remindersId;
    }

    public void setRemindersId(Set<Integer> remindersId) {
        this.remindersId = remindersId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

//    @Override
//    public String toString() {
//        return "EvenimentDto{" +
//                "id=" + id +
//                ", nume='" + nume + '\'' +
//                ", dataEveniment=" + dataEveniment +
//                ", oraEveniment=" + oraEveniment +
//                ", contId=" + contId +
//                ", remindersId=" + remindersId +
//                '}';
//    }

    @Override
    public String toString() {
        return  eventHour + " : " + name;
    }

}
