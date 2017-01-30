package com.ubs.opsit.interviews.berlinclock;

import java.util.regex.Pattern;

/**
 * A package visible parser to extract hours, minutes and seconds
 * from a string representing a time instant in format HH:mm:ss.
 */
class Parser {

    // Regular expression to validate time string
    private static final String TIME_PATTERN
            = "^([0-1][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]$";

    // Indexes of substrings parse time values
    private static final int HR_START = 0;
    private static final int HR_END = 2;
    private static final int MIN_START = 3;
    private static final int MIN_END = 5;
    private static final int SEC_START = 6;

    private static final int DECIMAL_RADIX = 10;

    public TimeValues parseTimeString(String timeString)
            throws IllegalArgumentException {

        validateTimeString(timeString);

        final int hours = Integer.parseInt(
                timeString.substring(HR_START, HR_END), DECIMAL_RADIX);
        final int minutes = Integer.parseInt(
                timeString.substring(MIN_START, MIN_END), DECIMAL_RADIX);
        final int seconds = Integer.parseInt(
                timeString.substring(SEC_START), DECIMAL_RADIX);

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
}
