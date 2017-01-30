package com.ubs.opsit.interviews.berlinclock;

/**
 * Line of lamps
 */
class Line {

    /** Character representation of the yellow color */
    public static final char YELLOW = 'Y';

    /** Character representation of the red color */
    public static final char RED = 'R';

    /** Character representation of a turned off lamp */
    public static final char OFF = 'O';

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
                YELLOW, YELLOW};

    private final char[] colors;

    private int lampsOnCount;

    private Line(char[] lamps) {
        this.colors = lamps;
    }

    /**
     * @return new line of red lamps
     */
    public static Line red() {
        return new Line(LAMPS_RED);
    }

    /**
     * @return new line of yellow lamps
     */
    public static Line yellow() {
        return new Line(LAMPS_YELLOW);
    }

    /**
     * @return new line of lamps in order YYRYYRYYRYY,
     *      where Y is a yellow lamp, and R is a red lamp
     */
    public static Line yellowAndRed() {
        return new Line(LAMPS_YELLOW_RED);
    }

    /**
     * @param lampsOnCount count of lamps which must be turned on
     *      (the other lamps remain off)
     */
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
