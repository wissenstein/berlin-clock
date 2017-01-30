package com.ubs.opsit.interviews.berlinclock;

import java.util.regex.Pattern;

/**
 * A package visible parser to extract hours, minutes and seconds
 * from a string representing a time instant in format HH:mm:ss.
 */
class Parser {

    // Regular expression to validate time string
    private static final String TIME_PATTERN = "^\\d\\d:\\d\\d:\\d\\d$";

    // Indexes of substrings parse time values
    private static final int HR_START = 0;
    private static final int HR_END = 2;
    private static final int MIN_START = 3;
    private static final int MIN_END = 5;
    private static final int SEC_START = 6;

    private static final int DECIMAL_RADIX = 10;

    // Maximum bounds to validate time values
    private static final int MAX_HOURS = 24;
    private static final int MAX_MINUTES = 59;
    private static final int MAX_SECONDS = 59;

    public TimeValues parseTimeString(String timeString)
            throws IllegalArgumentException {

        validateTimeString(timeString);

        final int hours = Integer.parseInt(
                timeString.substring(HR_START, HR_END), DECIMAL_RADIX);
        final int minutes = Integer.parseInt(
                timeString.substring(MIN_START, MIN_END), DECIMAL_RADIX);
        final int seconds = Integer.parseInt(
                timeString.substring(SEC_START), DECIMAL_RADIX);

        validateTimeValues(hours, minutes, seconds);

        return new TimeValues(hours, minutes, seconds);
    }

    private void validateTimeString(String timeString)
            throws IllegalArgumentException {

        Pattern isoTimePattern = Pattern.compile(TIME_PATTERN);

        if (!isoTimePattern.matcher(timeString).matches()) {
            throw new IllegalArgumentException(
                    "The passed time string [" + timeString
                            + "] doesn't match to required format [HH:mm:ss]");
        }
    }

    private void validateTimeValues(
            final int hours, final int minutes, final int seconds
    ) throws IllegalArgumentException {
        // as a time string is allowed to contain
        // only unsigned numbers and colons, no negative number can appear.
        // Therefore we check only maximum values

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
        }
    }
}
