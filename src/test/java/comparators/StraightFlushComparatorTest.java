package comparators;

import model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static comparators.ComparisonResult.*;
import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StraightFlushComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final StraightFlushComparator testee = new StraightFlushComparator(highCardComparator);

    @Test
    void compare_shouldReturnTie_whenNoHandHasStraightFlush() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TEN),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(TIE);
    }

    @Test
    void compare_shouldReturnFirstHandWin_whenOnlyFirstHandHasStraightFlush() {
        var firstHand = createHand(
                new Card(CLUBS, FOUR),
                new Card(CLUBS, EIGHT),
                new Card(CLUBS, FIVE),
                new Card(CLUBS, SEVEN),
                new Card(CLUBS, SIX)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TEN),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(FIRST_HAND_WIN);
    }

    @Test
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasStraightFlush() {
        var firstHand = createHand(
                new Card(CLUBS, FOUR),
                new Card(CLUBS, EIGHT),
                new Card(CLUBS, FIVE),
                new Card(CLUBS, SEVEN),
                new Card(DIAMONDS, SIX)
        );
        var secondHand = createHand(
                new Card(HEARTS, ACE),
                new Card(HEARTS, TWO),
                new Card(HEARTS, FIVE),
                new Card(HEARTS, FOUR),
                new Card(HEARTS, THREE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @ParameterizedTest
    @EnumSource(ComparisonResult.class)
    void compare_shouldReturnResultOfHighCardComparator_whenBothHandsHaveStraightFlush(
            ComparisonResult comparisonResult
    ) {
        var firstHand = createHand(
                new Card(CLUBS, FOUR),
                new Card(CLUBS, EIGHT),
                new Card(CLUBS, FIVE),
                new Card(CLUBS, SEVEN),
                new Card(CLUBS, SIX)
        );
        var secondHand = createHand(
                new Card(HEARTS, ACE),
                new Card(HEARTS, TWO),
                new Card(HEARTS, FIVE),
                new Card(HEARTS, FOUR),
                new Card(HEARTS, THREE)
        );

        when(highCardComparator.compareValues(EIGHT, FIVE)).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
    }
}