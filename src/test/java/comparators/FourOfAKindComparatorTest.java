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

class FourOfAKindComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final FourOfAKindComparator testee = new FourOfAKindComparator(highCardComparator);

    @Test
    void compare_shouldReturnTie_whenNoHandsHaveFourOfAKind() {
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
    void compare_shouldReturnFirstHandWin_whenOnlyFistHandHasFourOfAKind() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, ACE),
                new Card(HEARTS, ACE)
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
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasFourOfAKind() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, TWO)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TEN),
                new Card(SPADES, TEN),
                new Card(HEARTS, TEN),
                new Card(DIAMONDS, TEN),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @ParameterizedTest
    @EnumSource(value = ComparisonResult.class)
    void compare_shouldReturnResultOfHighCardComparator_whenBothHandsHaveFourOfAKind(
            ComparisonResult comparisonResult
    ) {
        var firstHand = createHand(
                new Card(CLUBS, THREE),
                new Card(SPADES, THREE),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, THREE),
                new Card(HEARTS, THREE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, FOUR),
                new Card(SPADES, TEN),
                new Card(HEARTS, FOUR),
                new Card(DIAMONDS, FOUR),
                new Card(SPADES, FOUR)
        );

        when(highCardComparator.compareValues(THREE, FOUR)).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
    }
}