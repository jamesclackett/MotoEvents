package com.jimboidin.motoevents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * A MotoEvent Object contains the information that represents
 * a single race.
 * <br><br>sport - represents the type of race (MotoGP or WSBK)
 * <br>description - a description of the race venue
 * <br>simpleDate - the race date (formatted: dd MM)
 * <br>date - the race date, as received from API
 */
public class MotoEvent {
    private String sport, description, simpleDate;
    private LocalDate date;

    public MotoEvent(String sport, String description, LocalDate date){
        this.sport = sport;
        this.description = description;
        this.date = date;
        simpleDate = getSimpleDate();
    }

    public String getSport(){
        return sport;
    }

    public String getDescription(){
        return description;
    }

    public LocalDate getDate(){
        return date;
    }

    public String getSimpleDate(){
        return date.format(DateTimeFormatter.ofPattern("dd MMMM"));
    }
}
