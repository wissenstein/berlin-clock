package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;

public class BerlinClock implements TimeConverter {

    public static final String NEWLINE = System.lineSeparator();

    private static final int DECIMAL = 10;
    private static final char YELLOW = 'Y';
    private static final char RED = 'R';
    private static final char OFF = 'O';
    private static final int STEP_HOURS = 5;
    private static final int STEP_MINUTES = 5;

    @Override
    public String convertTime(String aTime) {
        final int hours = Integer.parseInt(aTime.substring(0, 2), DECIMAL);
        final int minutes = Integer.parseInt(aTime.substring(3, STEP_HOURS), DECIMAL);
        final int seconds = Integer.parseInt(aTime.substring(6), DECIMAL);

        final String secLine = ((seconds % 2) == 0)
                ? String.valueOf(YELLOW)
                : String.valueOf(OFF);

        final char[] hrLine1Buffer = {OFF, OFF, OFF, OFF};
        final int hrQuotient = hours / STEP_HOURS;
        for (int i = 0; i < hrQuotient; i++) {
            hrLine1Buffer[i] = RED;
        }
        final String hrLine1 = new String(hrLine1Buffer);

        final char[] hrLine2Buffer = {OFF, OFF, OFF, OFF};
        final int hrReminder = hours % STEP_HOURS;
        for (int i = 0; i < hrReminder; i++) {
            hrLine2Buffer[i] = RED;
        }
        final String hrLine2 = new String(hrLine2Buffer);

        final char[] minLine1Buffer
                = {OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF, OFF};
        final int minQuotient = minutes / STEP_MINUTES;
        for (int i = 0; i < minQuotient; i++) {
            minLine1Buffer[i] = YELLOW;
        }
        final String minLine1 = new String(minLine1Buffer);

        return secLine + NEWLINE +
                hrLine1 + NEWLINE +
                hrLine2 + NEWLINE +
                minLine1 + NEWLINE +
                "OOOO";
    }
}
