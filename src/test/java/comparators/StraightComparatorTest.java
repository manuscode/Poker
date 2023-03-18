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

class StraightComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final StraightComparator testee = new StraightComparator(highCardComparator);

    @Test
    void compare_shouldReturnNoMatch_whenNoHandHasStraight() {
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

        assertThat(result).isEqualTo(NO_MATCH);
    }

    @Test
    void compare_shouldReturnFirstHandWin_whenOnlyFirstHandHasStraight() {
        var firstHand = createHand(
                new Card(CLUBS, TEN),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, EIGHT),
                new Card(HEARTS, SIX),
                new Card(HEARTS, NINE)
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
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasStraight() {
        var firstHand = createHand(
                new Card(CLUBS, TEN),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, EIGHT),
                new Card(HEARTS, KING),
                new Card(HEARTS, NINE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TEN),
                new Card(SPADES, QUEEN),
                new Card(HEARTS, KING),
                new Card(DIAMONDS, JACK),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @ParameterizedTest
    @EnumSource(ComparisonResult.class)
    void compare_shouldReturnResultOfHighCardComparator_whenBothHandsHaveStraights(ComparisonResult comparisonResult) {
        var firstHand = createHand(
                new Card(CLUBS, TEN),
                new Card(SPADES, SEVEN),
                new Card(DIAMONDS, EIGHT),
                new Card(HEARTS, SIX),
                new Card(HEARTS, NINE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, FOUR),
                new Card(HEARTS, FIVE),
                new Card(DIAMONDS, THREE),
                new Card(SPADES, ACE)
        );

        when(highCardComparator.compareValues(TEN, FIVE)).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
    }
}