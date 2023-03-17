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

class ThreeOfAKindComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final ThreeOfAKindComparator testee = new ThreeOfAKindComparator(highCardComparator);

    @Test
    void compare_shouldReturnDraw_whenNoHandHasThreeOfAKind() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
                new Card(HEARTS, THREE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TEN),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(DRAW);

    }

    @Test
    void compare_shouldReturnFirstHandWin_whenOnlyFirstHandHasThreeOfAKind() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, ACE),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
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
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasThreeOfAKind() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, QUEEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
                new Card(HEARTS, ACE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, TEN),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, SEVEN)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);

    }

    @ParameterizedTest
    @EnumSource(ComparisonResult.class)
    void compare_shouldReturnResultOfHighCardComparator_whenBothHandsHaveThreeOfAKind(
            ComparisonResult comparisonResult
    ) {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, QUEEN),
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TWO),
                new Card(HEARTS, ACE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, TEN),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, SEVEN),
                new Card(SPADES, SEVEN)
        );

        when(highCardComparator.compareValues(ACE, SEVEN)).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);

    }
}