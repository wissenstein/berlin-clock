package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;

public class BerlinClock implements TimeConverter {

    public static final String NEWLINE = System.lineSeparator();

    private static final int DECIMAL = 10;
    private static final char YELLOW = 'Y';
    private static final char RED = 'R';
    private static final char OFF = 'O';
    private static final int STEP_HR = 5;
    private static final int STEP_MIN_YELLOW = 5;
    private static final int STEP_MIN_RED = 15 / STEP_MIN_YELLOW;

    @Override
    public String convertTime(String aTime) {
        final int hours = Integer.parseInt(aTime.substring(0, 2), DECIMAL);
        final int minutes = Integer.parseInt(aTime.substring(3, STEP_HR), DECIMAL);
        final int seconds = Integer.parseInt(aTime.substring(6), DECIMAL);

        final String secLine = ((seconds % 2) == 0)
                ? String.valueOf(YELLOW)
                : String.valueOf(OFF);

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

        return secLine + NEWLINE +
                new String(hrLine1Buffer) + NEWLINE +
                new String(hrLine2Buffer) + NEWLINE +
                new String(minLine1Buffer) + NEWLINE +
                new String(minLine2Buffer);
    }
}
