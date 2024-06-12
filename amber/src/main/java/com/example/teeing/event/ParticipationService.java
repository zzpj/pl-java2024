package com.example.teeing.event;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class Guest {
    private final String name;
    private boolean participating;
    private Integer participantsNumber;

    public Guest(String name, boolean participating,
                 Integer participantsNumber) {
        this.name = name;
        this.participating = participating;
        this.participantsNumber = participantsNumber;
    }

    public String getName() {
        return name;
    }

    public boolean isParticipating() {
        return participating;
    }

    public Integer getParticipantsNumber() {
        return participantsNumber;
    }
}

class EventParticipation {
    private final List<String> guestNameList;
    private final Integer totalNumberOfParticipants;

    public EventParticipation(List<String> guestNameList,
                              Integer totalNumberOfParticipants) {
        this.guestNameList = guestNameList;
        this.totalNumberOfParticipants = totalNumberOfParticipants;
    }

    public List<String> getGuestNameList() {
        return guestNameList;
    }

    public Integer getTotalNumberOfParticipants() {
        return totalNumberOfParticipants;
    }

}

public class ParticipationService {

    EventParticipation processEventGuests(List<Guest> guests) {
        return guests.stream().collect(Collectors.teeing(
                Collectors.filtering(Guest::isParticipating,
                        Collectors.mapping(Guest::getName, Collectors.toList())),
                Collectors.summingInt(Guest::getParticipantsNumber),
                EventParticipation::new));
    }
}
