package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static com.ubs.opsit.interviews.berlinclock.BerlinClock.NEWLINE;
import static org.assertj.core.api.Assertions.assertThat;

public class BerlinClockTest {

    private final TimeConverter berlinClock = new BerlinClock();

    private final int HOURS1 = 1;
    private final int HOURS2 = 2;
    private final int MINUTES1 = 3;
    private final int MINUTES2 = 4;

    @Test
    public void secondLampShouldBeYellowAtEvenSecond() {
        // arrange
        final String timeWith2Sec = "00:00:02";

        // act
        final String output = berlinClock.convertTime(timeWith2Sec);

        // assert
        final String secLine = output.substring(0, 1);
        assertThat(secLine).isEqualTo("Y");
    }

    @Test
    public void secondLampShouldBeOffAtOddSecond() {
        // arrange
        final String timeWith5Sec = "00:00:05";

        // act
        final String output = berlinClock.convertTime(timeWith5Sec);

        // assert
        final String secLine = output.substring(0, 1);
        assertThat(secLine).isEqualTo("O");
    }

    @Test
    public void firstHourLineShouldBeEmptyAtLessThan5Hours() {
        // arrange
        final String timeWith3Hr = "03:22:47";

        // act
        final String output = berlinClock.convertTime(timeWith3Hr);

        // assert
        assertThat(extractLine(output, HOURS1)).isEqualTo("OOOO");
    }

    @Test
    public void firstHourLineShouldBePartiallyFilledBetween5And19Hours() {
        // arrange
        final String timeWith14Hr = "14:00:00";

        // act
        final String output = berlinClock.convertTime(timeWith14Hr);

         // assert
        assertThat(extractLine(output, HOURS1)).isEqualTo("RROO");
    }

    @Test
    public void firstHourLineShouldBeFullAtMoreThan19Hours() {
        // arrange
        final String timeWith21Hr = "21:25:19";

        // act
        final String output = berlinClock.convertTime(timeWith21Hr);

        // assert
        assertThat(extractLine(output, HOURS1)).isEqualTo("RRRR");
    }

    @Test
    public void secondHourLineShouldBeEmptyAtMultiple5Hours() {
        // arrange
        final String timeWith15Hr = "15:29:39";

        // act
        final String output = berlinClock.convertTime(timeWith15Hr);

        // assert
        assertThat(extractLine(output, HOURS2)).isEqualTo("OOOO");
    }

    @Test
    public void secondHourLineShouldHaveRedLampsOnAtNoMultiple5Hours() {
        // arrange
        final String timeWith21Hr = "21:00:00";

        // act
        final String output = berlinClock.convertTime(timeWith21Hr);

        // assert
        assertThat(extractLine(output, HOURS2)).isEqualTo("ROOO");
    }

    @Test
    public void firstMinuteLineShouldbeEmptyAtLessThan5Minutes() {
        // arrange
        final String timeWith4Min = "03:04:24";

        // act
        final String output = berlinClock.convertTime(timeWith4Min);

        // assert
        assertThat(extractLine(output, MINUTES1)).isEqualTo("OOOOOOOOOOO");
    }

    @Test
    public void firstMinuteLineShouldHaveYellowLampsOnBetween5And14Minutes() {
        // arrange
        final String timeWith10Min = "18:10:27";

        // act
        final String output
                = berlinClock.convertTime(timeWith10Min);

        // assert
        assertThat(extractLine(output, MINUTES1)).isEqualTo("YYOOOOOOOOO");
    }

    @Test
    public void firstMinuteLineShouldHaveRedLampsOnForMultiple15Minutes() {
        // arrange
        final String timeWith30Min = "19:30:40";

        // act
        final String output = berlinClock.convertTime(timeWith30Min);

        // assert
        assertThat(extractLine(output, MINUTES1)).isEqualTo("YYRYYROOOOO");
    }

    @Test
    public void secondMiunteLineShouldBeEmptyAtMultiple5Mitutes() {
        // arrange
        final String timeWith45Min = "16:45:11";

        // act
        final String output = berlinClock.convertTime(timeWith45Min);

        // assert
        assertThat(extractLine(output, MINUTES2)).isEqualTo("OOOO");
    }

    @Test
    public void secondMinuteLineShouldHaveYellowLampsOnAtNoMultiple5Minutes() {
        // arrange
        final String timeWith6Min = "20:06:07";

        // act
        final String output = berlinClock.convertTime(timeWith6Min);

        // assert
        assertThat(extractLine(output, MINUTES2)).isEqualTo("YOOO");
    }

    @Test
    public void convertFirstStateOfDay() {
        // arrange
        final String midnight = "00:00:00";
        final String expectedBerlinerClockState = layoutAsString(
                "Y",
                "OOOO",
                "OOOO",
                "OOOOOOOOOOO",
                "OOOO");

        // act
        final String output = berlinClock.convertTime(midnight);

        // assert
        assertThat(output).isEqualTo(expectedBerlinerClockState);
    }

    @Test
    public void convertLastStateOfDay() {
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
