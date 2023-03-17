package comparators;

import model.Card;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;

import static comparators.ComparisonResult.*;
import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FullHouseComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final FullHouseComparator testee = new FullHouseComparator(highCardComparator);

    @Test
    void compare_shouldReturnDraw_whenNoHandHasFullHouse() {
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

        assertThat(result).isEqualTo(DRAW);
    }

    @Test
    void compare_shouldReturnFirstHandWin_whenOnlyFirstHandHasFullHouse() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
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
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasFullHouse() {
        var firstHand = createHand(
                new Card(CLUBS, EIGHT),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, ACE),
                new Card(HEARTS, TEN),
                new Card(HEARTS, ACE)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TWO),
                new Card(HEARTS, JACK),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, JACK)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @ParameterizedTest
    @EnumSource(value = ComparisonResult.class)
    void compare_shouldResultOfHighCardComparator_whenBothHandsHaveFullHouse(ComparisonResult comparisonResult) {
        var firstHand = createHand(
                new Card(CLUBS, EIGHT),
                new Card(SPADES, EIGHT),
                new Card(DIAMONDS, EIGHT),
                new Card(HEARTS, TEN),
                new Card(HEARTS, TEN)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TWO),
                new Card(HEARTS, JACK),
                new Card(DIAMONDS, TWO),
                new Card(SPADES, JACK)
        );

        when(highCardComparator.compareSortedValueLists(List.of(EIGHT, TEN), List.of(TWO, JACK)))
                .thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
    }
}