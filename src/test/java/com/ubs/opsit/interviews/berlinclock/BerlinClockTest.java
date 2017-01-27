package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static com.ubs.opsit.interviews.berlinclock.BerlinClock.NEWLINE;

public class BerlinClockTest {

    private final TimeConverter berlinClock = new BerlinClock();

    private final int SECONDS = 0;
    private final int HOURS1 = 1;
    private final int HOURS2 = 2;
    private final int MINUTES1 = 3;
    private final int MINUTES2 = 4;

    @Test
    public void secondLampShouldBeYellowAtEvenSecond() {
        // arrange
        final String timeWithEvenSeconds = "00:00:02";

        // act
        final String output = berlinClock.convertTime(timeWithEvenSeconds);

        // assert
        final String secLine = output.substring(0, 1);
        assertThat(secLine).isEqualTo("Y");
    }

    @Test
    public void secondLampShouldBeOffAtOddSecond() {
        // arrange
        final String timeWithOddSeconds = "00:00:05";

        // act
        final String output = berlinClock.convertTime(timeWithOddSeconds);

        // assert
        final String secLine = output.substring(0, 1);
        assertThat(secLine).isEqualTo("O");
    }

    @Test
    public void firstHourLineShouldBePartiallyFilledBetween5And19Hours() {
        // arrange
        final String timeWithMoreThan4AndLessThan20Hr = "14:00:00";

        // act
        final String output
                = berlinClock.convertTime(timeWithMoreThan4AndLessThan20Hr);

         // assert
        assertThat(extractLine(output, HOURS1)).isEqualTo("RROO");
    }

    @Test
    public void secondHourLineShouldBePartiallyFilledAtNoMultiple5Hours() {
        // arrange
        final String timeWithNoMultiple5Hr = "21:00:00";

        // act
        final String output = berlinClock.convertTime(timeWithNoMultiple5Hr);

        // assert
        assertThat(extractLine(output, HOURS2)).isEqualTo("ROOO");
    }

    @Test
    public void firstMinuteLineShouldBePartiallyFilledYellowAtLessThan15Minutes() {
        // arrange
        final String timeWithMultiple5Min = "18:10:27";

        // act
        final String output = berlinClock.convertTime(timeWithMultiple5Min);

        // assert
        assertThat(extractLine(output, MINUTES1)).isEqualTo("YYOOOOOOOOO");
    }

    @Test
    public void firstMinuteLineShouldHaveRedLampsForMultiple15Minutes() {
        // arrange
        final String timeWithMultiple15Min = "19:30:40";

        // act
        final String output = berlinClock.convertTime(timeWithMultiple15Min);

        // assert
        assertThat(extractLine(output, MINUTES1)).isEqualTo("YYRYYROOOOO");
    }

    @Test
    public void secondMinuteLineShoulBePartiallyFilledAtNoMultiple5Minutes() {
        // arrange
        final String timeWithNoMultiple5Min = "20:06:07";

        // act
        final String output = berlinClock.convertTime(timeWithNoMultiple5Min);

        // assert
        assertThat(extractLine(output, MINUTES2)).isEqualTo("YOOO");
    }

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
    public void convertTimeBeforeMidnight() {
        // arrange
        final String lastMinuteOfDay = "23:59:59";
        final String expectedBerlinClockState = layoutAsString(
                "O",
                "RRRR",
                "RRRO",
                "YYRYYRYYRYY",
                "YYYY");

        // act
        final String output = berlinClock.convertTime(lastMinuteOfDay);

        // assert
        assertThat(output).isEqualTo(expectedBerlinClockState);
    }

    @Test
    public void convertEarlyMorning() {
        // arrange
        final String timeWithLittleHoursMinutesAndSeconds = "04:04:04";
        final String expectedBerlinClockState = layoutAsString(
                "Y",
                "OOOO",
                "RRRR",
                "OOOOOOOOOOO",
                "YYYY");

        // act
        final String output = berlinClock
                .convertTime(timeWithLittleHoursMinutesAndSeconds);

        // assert
        assertThat(output).isEqualTo(expectedBerlinClockState);
    }

    private String extractLine(final String output, final int lineIndex) {
        final String[] lines = output.split(NEWLINE);
        final String line = lines[lineIndex];
        return line;
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
