package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;
import java.util.regex.Pattern;

/**
 * Implementation of {@link TimeConverter}, which converts a string
 * representing time in ISO format to five lines
 * representing this time as a state of Berlin Clock
 * aka Berlin-Uhr aka Mengenlehreuhr.
 */
public class BerlinClock implements TimeConverter {

    public static final String NEWLINE = System.lineSeparator();

    private static final int DECIMAL_RADIX = 10;

    private static final char YELLOW = 'Y';
    private static final char RED = 'R';
    private static final char OFF = 'O';
    private static final char[] LAMPS_RED
            = {RED, RED, RED, RED};
    private static final char[] LAMPS_YELLOW
            = {YELLOW, YELLOW, YELLOW, YELLOW};
    private static final char[] LAMPS_YELLOW_RED
            = {
                YELLOW, YELLOW,
                RED,
                YELLOW, YELLOW,
                RED,
                YELLOW, YELLOW,
                RED,
                YELLOW, YELLOW
            };

    private static final int STEP = 5;
    private static final int MAX_HOURS = 24;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_SECONDS = 59;

    // indexes of substrings and regular expression to validate and parse time values
    private static final int HR_START = 0;
    private static final int HR_END = 2;
    private static final int MIN_START = 3;
    private static final int MIN_END = 5;
    private static final int SEC_START = 6;
    private static final String TIME_PATTERN = "^\\d\\d:\\d\\d:\\d\\d$";

    // Data transfer object to parse time values from ISO time string
    private static final class TimeValues {

        private final int hours;
        private final int minutes;
        private final int seconds;

        TimeValues(int hours, int minutes, int seconds) {
            this.hours = hours;
            this.minutes = minutes;
            this.seconds = seconds;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getSeconds() {
            return seconds;
        }
    }

    private static final class Line {
        private final char[] colors;
        private int lampsOnCount;

        public Line(char[] lamps) {
            this.colors = lamps;
        }

        public void setLampsOnCount(int lampsOnCount) {
            this.lampsOnCount = lampsOnCount;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lampsOnCount; i++) {
                builder.append(colors[i]);
            }
            for (int i = lampsOnCount; i < colors.length; i++) {
                builder.append(OFF);
            }

            return builder.toString();
        }
    }

    private TimeValues validateAndParseTimeString(String aTime)
            throws IllegalArgumentException {

        Pattern isoTimePattern = Pattern.compile(TIME_PATTERN);
        if (!isoTimePattern.matcher(aTime).matches()) {
            throw new IllegalArgumentException(
                    "The passed time string [" + aTime
                    + "] doesn't match to required format [HH:mm:ss]");
        } else {
            // as a time string is allowed to contain
            // only unsigned numbers and colons, no negative number can appear.
            // Therefore we check only maximum values

            final int hours = Integer.parseInt(
                    aTime.substring(HR_START, HR_END), DECIMAL_RADIX);
            final int minutes = Integer.parseInt(
                    aTime.substring(MIN_START, MIN_END), DECIMAL_RADIX);
            final int seconds = Integer.parseInt(
                    aTime.substring(SEC_START), DECIMAL_RADIX);

            if (hours > MAX_HOURS) {
                throw new IllegalArgumentException(
                        "Hours value exceeds maximum limit of ["
                        + MAX_HOURS + "]");
            } else if (minutes > MAX_MINUTES) {
                throw new IllegalArgumentException(
                        "Minutes value exceeds maximum limit of ["
                        + MAX_MINUTES + "]");
            } else if (seconds > MAX_SECONDS) {
                throw new IllegalArgumentException(
                        "Seconds value exceeds maximum limit of ["
                        + MAX_SECONDS + "]");
            } else {
                return new TimeValues(hours, minutes, seconds);
            }
        }
    }

    /**
     * Converts digital representation of time to Berlin Clock representation.
     *
     * @param aTime string representing the time in format HH:mm:ss
     * @return 5 lines representing Berlin Clock state
     *      corresponding to to the passed time
     * @throws IllegalArgumentException if {@code aTime}
     *      does not correspond the required format
     */
    @Override
    public String convertTime(String aTime) throws IllegalArgumentException {
        final TimeValues timeValues = validateAndParseTimeString(aTime);

        final Line hrLine1 = new Line(LAMPS_RED);
        final Line hrLine2 = new Line(LAMPS_RED);
        final Line minLine1 = new Line(LAMPS_YELLOW_RED);
        final Line minLine2 = new Line(LAMPS_YELLOW);

        final boolean secondsValueIsEven = (timeValues.getSeconds() % 2) == 0;
        final char secLamp = secondsValueIsEven ? YELLOW : OFF;

        final int hours = timeValues.getHours();
        hrLine1.setLampsOnCount(hours / STEP);
        hrLine2.setLampsOnCount(hours % STEP);

        final int minutes = timeValues.getMinutes();
        minLine1.setLampsOnCount(minutes / STEP);
        minLine2.setLampsOnCount(minutes % STEP);

        return secLamp + NEWLINE
                + hrLine1.toString() + NEWLINE
                + hrLine2.toString() + NEWLINE
                + minLine1.toString() + NEWLINE
                + minLine2.toString();
    }
}
