package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static com.ubs.opsit.interviews.berlinclock.BerlinClock.NEWLINE;
import static org.assertj.core.api.Assertions.assertThat;

public class BerlinClockTest {

    private final TimeConverter berlinClock = new BerlinClock();

    private final int SECONDS = 0;
    private final int HOURS1 = 1;
    private final int HOURS2 = 2;
    private final int MINUTES1 = 3;
    private final int MINUTES2 = 4;

    @Test
    public void convertMidnight() {
        // arrange
        final String midnight = "00:00:00";
        final String expectedBerlinerMidnight = layoutAsString(
                "Y",
                "OOOO",
                "OOOO",
                "OOOOOOOOOOO",
                "OOOO");

        // act
        final String berlinerMidnight = berlinClock.convertTime(midnight);

        // assert
        assertThat(berlinerMidnight).isEqualTo(expectedBerlinerMidnight);
    }

    @Test
    public void testEvenSecond() {
        // arrange
        final String timeWithEvenSeconds = "00:00:02";

        // act
        final String output = berlinClock.convertTime(timeWithEvenSeconds);

        // assert
        final String secLine = output.substring(0, 1);
        assertThat(secLine).isEqualTo("Y");
    }

    @Test
    public void testOddSecond() {
        // arrange
        final String timeWithOddSeconds = "00:00:05";

        // act
        final String output = berlinClock.convertTime(timeWithOddSeconds);

        // assert
        final String secLine = output.substring(0, 1);
        assertThat(secLine).isEqualTo("O");
    }

    @Test
    public void testFirstHourLine() {
        // arrange
        final String fourteenHundredHours = "14:00:00";

        // act
        final String output = berlinClock.convertTime(fourteenHundredHours);

        // assert
        final String[] lines = output.split(NEWLINE);
        assertThat(lines[HOURS1]).isEqualTo("RROO");
    }

    @Test
    public void testSecondHourLine() {
        // arrange
        final String twentyOneHundredHours = "21:00:00";

        // act
        final String output = berlinClock.convertTime(twentyOneHundredHours);

        // assert
        final String[] lines = output.split(NEWLINE);
        assertThat(lines[HOURS2]).isEqualTo("ROOO");
    }

    @Test
    public void testYellowPlacesInFirstMinuteLine() {
        // arrange
        final String timeWith10Minutes = "18:10:27";

        // act
        final String output = berlinClock.convertTime(timeWith10Minutes);

        // assert
        final String[] lines = output.split(NEWLINE);
        assertThat(lines[MINUTES1]).isEqualTo("YYOOOOOOOOO");
    }

    private String layoutAsString(
            String secLine,
            String hrLine1, String hrLine2,
            String minLine1, String minLine2) {

        return secLine + NEWLINE
                + hrLine1 + NEWLINE
                + hrLine2 + NEWLINE
                + minLine1 + NEWLINE
                + minLine2;
    }
}
