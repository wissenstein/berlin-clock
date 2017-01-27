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

    // radix to parse time values
    private static final int DECIMAL = 10;

    private static final char YELLOW = 'Y';
    private static final char RED = 'R';
    private static final char OFF = 'O';
    private static final int STEP_HR = 5;
    private static final int STEP_MIN_YELLOW = 5;
    private static final int STEP_MIN_RED = 15 / STEP_MIN_YELLOW;
    private static final int MAX_HOURS = 24;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_SECONDS = 59;

    // Data transfer object to parse time values from ISO time string
    private static final class TimeValuesDto {

        private final int hours;
        private final int minutes;
        private final int seconds;

        TimeValuesDto(int hours, int minutes, int seconds) {
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

    @Override
    public String convertTime(String aTime) throws IllegalArgumentException {
        TimeValuesDto timeValues = validateAndParseTimeString(aTime);

        final String secLine = ((timeValues.getSeconds() % 2) == 0)
                ? String.valueOf(YELLOW)
                : String.valueOf(OFF);

        final int hours = timeValues.getHours();

        final char[] hrLine1Buffer = {OFF, OFF, OFF, OFF};
        final int hrQuotient = hours / STEP_HR;
        for (int i = 0; i < hrQuotient; i++) {
            hrLine1Buffer[i] = RED;
        }

        final char[] hrLine2Buffer = {OFF, OFF, OFF, OFF};
        final int hrReminder = hours % STEP_HR;
        for (int i = 0; i < hrReminder; i++) {
            hrLine2Buffer[i] = RED;
        }

        final int minutes = timeValues.getMinutes();

        final char[] minLine1Buffer
                = {OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF};
        final int minQuotient5 = minutes / STEP_MIN_YELLOW;
        for (int i = 0; i < minQuotient5; i++) {
            minLine1Buffer[i] = YELLOW;
        }
        for (int i = STEP_MIN_RED - 1; i < minQuotient5; i += STEP_MIN_RED) {
            minLine1Buffer[i] = RED;
        }

        final char[] minLine2Buffer = {OFF, OFF, OFF, OFF};
        final int minReminder = minutes % STEP_MIN_YELLOW;
        for (int i = 0; i < minReminder; i++) {
            minLine2Buffer[i] = YELLOW;
        }

        return secLine + NEWLINE
                + new String(hrLine1Buffer) + NEWLINE
                + new String(hrLine2Buffer) + NEWLINE
                + new String(minLine1Buffer) + NEWLINE
                + new String(minLine2Buffer);
    }

    private TimeValuesDto validateAndParseTimeString(String aTime)
            throws IllegalArgumentException {

        Pattern isoTimePattern = Pattern.compile("^\\d\\d:\\d\\d:\\d\\d$");
        if (!isoTimePattern.matcher(aTime).matches()) {
            throw new IllegalArgumentException(
                    "The passed time string [" + aTime
                    + "] doesn't match to required format [HH:mm:ss]");
        } else {
            final int hours
                    = Integer.parseInt(aTime.substring(0, 2), DECIMAL);
            final int minutes
                    = Integer.parseInt(aTime.substring(3, 5), DECIMAL);
            final int seconds
                    = Integer.parseInt(aTime.substring(6), DECIMAL);

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
                return new TimeValuesDto(hours, minutes, seconds);
            }
        }
    }
}
