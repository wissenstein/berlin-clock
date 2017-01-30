package com.ubs.opsit.interviews.berlinclock;

/**
 * Package visible data transfer object to represent a time instant
 * as a combination of three integer values: hours, minutes and seconds
 */
class TimeValues {

    private final int hours;
    private final int minutes;
    private final int seconds;

    TimeValues(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public final int getHours() {
        return hours;
    }

    public final int getMinutes() {
        return minutes;
    }

    public final int getSeconds() {
        return seconds;
    }
}
