package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;

/**
 * Implementation of {@link TimeConverter}, which converts a string
 * representing time in ISO format to five lines
 * representing this time as a state of Berlin Clock
 * aka Berlin-Uhr aka Mengenlehreuhr.
 */
public class BerlinClock implements TimeConverter {

    private static final String NEWLINE = System.lineSeparator();
    private static final int STEP = 5;

    private final Parser parser = new Parser();

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
        final TimeValues timeValues = parser.parseTimeString(aTime);

        final Line hrLine1 = Line.red();
        final Line hrLine2 = Line.red();
        final Line minLine1 = Line.yellowAndRed();
        final Line minLine2 = Line.yellow();

        final boolean secondsValueIsEven = (timeValues.getSeconds() % 2) == 0;
        final char secLamp = secondsValueIsEven ? Line.YELLOW : Line.OFF;

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
