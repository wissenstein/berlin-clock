package com.ubs.opsit.interviews.berlinclock;

import com.ubs.opsit.interviews.TimeConverter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BerlinClockTest {

    // sample line: length:
    // O                 1
    // RROO              4
    // RRRR              4
    // YYRYYROOOOO      11
    // YYYY              4
    private static final int NL = System.lineSeparator().length();
    private static final int SEC_START = 0;
    private static final int SEC_END = SEC_START + 1;
    private static final int HR1_START = SEC_END + NL;
    private static final int HR1_END = HR1_START + 4;
    private static final int HR2_START = HR1_END + NL;
    private static final int HR2_END = HR2_START + 4;
    private static final int MIN1_START = HR2_END + NL;
    private static final int MIN1_END = MIN1_START + 11;
    private static final int MIN2_START = MIN1_END + NL;
    private static final int MIN2_END = MIN2_START + 4;

    private static enum Line {
        SECONDS(SEC_START, SEC_END),
        HOURS1(HR1_START, HR1_END),
        HOURS2(HR2_START, HR2_END),
        MINUTES1(MIN1_START, MIN1_END),
        MINUTES2(MIN2_START, MIN2_END);

        private final int startIndex;
        private final int endIndex;

        Line(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        int getStartIndex() {
            return startIndex;
        }

        int getEndIndex() {
            return endIndex;
        }
    }

    private final TimeConverter berlinClock = new BerlinClock();

    @Test
    public void secondLampShouldBeYellowAtEvenSecond() {
        // arrange
        final String timeWith2Sec = "00:00:02";

        // act
        final String output = berlinClock.convertTime(timeWith2Sec);

        // assert
        assertThat(extractLine(output, Line.SECONDS)).isEqualTo("Y");
    }

    @Test
    public void secondLampShouldBeOffAtOddSecond() {
        // arrange
        final String timeWith5Sec = "00:00:05";

        // act
        final String output = berlinClock.convertTime(timeWith5Sec);

        // assert
        assertThat(extractLine(output, Line.SECONDS)).isEqualTo("O");
    }

    @Test
    public void firstHourLineShouldBeEmptyAtLessThan5Hours() {
        // arrange
        final String timeWith3Hr = "03:22:47";

        // act
        final String output = berlinClock.convertTime(timeWith3Hr);

        // assert
        assertThat(extractLine(output, Line.HOURS1)).isEqualTo("OOOO");
    }

    @Test
    public void firstHourLineShouldBePartiallyFilledBetween5And19Hours() {
        // arrange
        final String timeWith14Hr = "14:00:00";

        // act
        final String output = berlinClock.convertTime(timeWith14Hr);

        // assert
        assertThat(extractLine(output, Line.HOURS1)).isEqualTo("RROO");
    }

    @Test
    public void firstHourLineShouldBeFullAtMoreThan19Hours() {
        // arrange
        final String timeWith21Hr = "21:25:19";

        // act
        final String output = berlinClock.convertTime(timeWith21Hr);

        // assert
        assertThat(extractLine(output, Line.HOURS1)).isEqualTo("RRRR");
    }

    @Test
    public void secondHourLineShouldBeEmptyAtMultiple5Hours() {
        // arrange
        final String timeWith15Hr = "15:29:39";

        // act
        final String output = berlinClock.convertTime(timeWith15Hr);

        // assert
        assertThat(extractLine(output, Line.HOURS2)).isEqualTo("OOOO");
    }

    @Test
    public void secondHourLineShouldHaveRedLampsOnAtNoMultiple5Hours() {
        // arrange
        final String timeWith21Hr = "21:00:00";

        // act
        final String output = berlinClock.convertTime(timeWith21Hr);

        // assert
        assertThat(extractLine(output, Line.HOURS2)).isEqualTo("ROOO");
    }

    @Test
    public void firstMinuteLineShouldbeEmptyAtLessThan5Minutes() {
        // arrange
        final String timeWith4Min = "03:04:24";

        // act
        final String output = berlinClock.convertTime(timeWith4Min);

        // assert
        assertThat(extractLine(output, Line.MINUTES1)).isEqualTo("OOOOOOOOOOO");
    }

    @Test
    public void firstMinuteLineShouldHaveYellowLampsOnBetween5And14Minutes() {
        // arrange
        final String timeWith10Min = "18:10:27";

        // act
        final String output
                = berlinClock.convertTime(timeWith10Min);

        // assert
        assertThat(extractLine(output, Line.MINUTES1)).isEqualTo("YYOOOOOOOOO");
    }

    @Test
    public void firstMinuteLineShouldHaveRedLampsOnForMultiple15Minutes() {
        // arrange
        final String timeWith30Min = "19:30:40";

        // act
        final String output = berlinClock.convertTime(timeWith30Min);

        // assert
        assertThat(extractLine(output, Line.MINUTES1)).isEqualTo("YYRYYROOOOO");
    }

    @Test
    public void secondMiunteLineShouldBeEmptyAtMultiple5Mitutes() {
        // arrange
        final String timeWith45Min = "16:45:11";

        // act
        final String output = berlinClock.convertTime(timeWith45Min);

        // assert
        assertThat(extractLine(output, Line.MINUTES2)).isEqualTo("OOOO");
    }

    @Test
    public void secondMinuteLineShouldHaveYellowLampsOnAtNoMultiple5Minutes() {
        // arrange
        final String timeWith9Min = "20:09:07";

        // act
        final String output = berlinClock.convertTime(timeWith9Min);

        // assert
        assertThat(extractLine(output, Line.MINUTES2)).isEqualTo("YYYY");
    }

    @Test
    public void shouldRejectTimeStringOfWrongStructure() {
        // arrange
        final String timeStringWithoutColons = "20-55-90";

        // act
        final Throwable exception = catchThrowable(() -> {
            berlinClock.convertTime(timeStringWithoutColons);
        });

        // assert
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldRejectHourValuesGreaterThan24() {
        // arrange
        final String timeStringWithMoreThan24Hr = "25:26:05";

        // act and assert
        final Throwable exception = catchThrowable(() -> {
            berlinClock.convertTime(timeStringWithMoreThan24Hr);
        });

        // assert
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldRejectMinuteValuesGreaterThan59() {
        // arrange
        final String timeStringWithMoreThan59Min = "13:60:42";

        // act and assert
        final Throwable exception = catchThrowable(() -> {
            berlinClock.convertTime(timeStringWithMoreThan59Min);
        });

        // assert
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldRejectSecondValuesGreaterThan59() {
        // arrange
        final String timeStringWithMoreThan59Sec = "14:51:60";

        // act and assert
        final Throwable exception = catchThrowable(() -> {
            berlinClock.convertTime(timeStringWithMoreThan59Sec);
        });

        // assert
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldRejectNulls() {
        // arrange
        final String nullTimeString = null;

        // act and assert
        final Throwable exception = catchThrowable(() -> {
            berlinClock.convertTime(nullTimeString);
        });

        // assert
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

    private String extractLine(final String output, final Line line) {
        return output.substring(line.getStartIndex(), line.getEndIndex());
    }
}
