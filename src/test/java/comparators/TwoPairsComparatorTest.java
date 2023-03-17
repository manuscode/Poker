package comparators;

import model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static comparators.ComparisonResult.*;
import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TwoPairsComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final TwoPairsComparator testee = new TwoPairsComparator(highCardComparator);

    @Test
    void compare_shouldReturnDraw_whenNoHandHasTwoPairs() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    void compare_shouldReturnFirstHandWin_whenOnlyFirstHandHasTwoPairs() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, ACE),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, JACK),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(FIRST_HAND_WIN);
    }

    @Test
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasTwoPairs() {
        var firstHand = createHand(
                new Card(CLUBS, KING),
                new Card(SPADES, ACE),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, JACK),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @ParameterizedTest
    @CsvSource({
            "FIRST_HAND_WIN",
            "SECOND_HAND_WIN"
    })
    void compare_shouldReturnResultOfHighCardComparator_whenBothHandsHaveTwoPairsAndResultIsNotDraw(
            ComparisonResult comparisonResult
    ) {
        var firstHand = createHand(
                new Card(CLUBS, KING),
                new Card(SPADES, KING),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, JACK),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, ACE),
                new Card(DIAMONDS, JACK)
        );

        when(highCardComparator.compareValueLists(any(), any())).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
        verify(highCardComparator, never()).compareCardLists(any(), any());
    }

    @ParameterizedTest
    @EnumSource(ComparisonResult.class)
    void compare_shouldReturnSecondResultOfHighCardComparator_whenFirstResultIsDraw(
            ComparisonResult comparisonResult
    ) {
        var firstHand = createHand(
                new Card(CLUBS, KING),
                new Card(SPADES, KING),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, JACK),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(SPADES, JACK),
                new Card(SPADES, ACE),
                new Card(DIAMONDS, JACK)
        );

        when(highCardComparator.compareValueLists(any(), any())).thenReturn(DRAW);
        when(highCardComparator.compareCardLists(any(), any())).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
    }
}