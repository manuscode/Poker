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
import static org.mockito.Mockito.*;

class PairComparatorTest {

    private final HighCardComparator highCardComparator = mock(HighCardComparator.class);

    private final PairComparator testee = new PairComparator(highCardComparator);

    @Test
    void compare_shouldReturnTie_whenNoHandHasPairAndSameValues() {
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
    void compare_shouldReturnFirstHandWin_whenOnlyFirstHandHasPair() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
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

        assertThat(result).isEqualTo(FIRST_HAND_WIN);
    }

    @Test
    void compare_shouldReturnSecondHandWin_whenOnlySecondHandHasPair() {
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
                new Card(DIAMONDS, SIX),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
    }

    @Test
    void compare_shouldReturnFirstHandWin_whenFirstHandHasHigherPair() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, TEN),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, SIX),
                new Card(SPADES, ACE)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(FIRST_HAND_WIN);
        verifyNoInteractions(highCardComparator);
    }

    @Test
    void compare_shouldReturnSecondHandWin_whenSecondHandHasHigherPair() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, QUEEN),
                new Card(HEARTS, THREE),
                new Card(DIAMONDS, SIX),
                new Card(SPADES, QUEEN)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(SECOND_HAND_WIN);
        verifyNoInteractions(highCardComparator);
    }

    @ParameterizedTest
    @EnumSource(ComparisonResult.class)
    void compare_shouldReturnResultOfHighCardComparator_whenBothHandsHaveEqualValuedPair(
            ComparisonResult comparisonResult
    ) {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, TWO),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, JACK),
                new Card(HEARTS, THREE),
                new Card(DIAMONDS, SIX),
                new Card(SPADES, JACK)
        );

        var firstHandCardList = firstHand.getCardList();
        var secdonHandCardList = secondHand.getCardList();

        when(highCardComparator.compareCardLists(
                eq(List.of(
                        firstHandCardList.get(0),
                        firstHandCardList.get(1),
                        firstHandCardList.get(3))
                ),
                eq(List.of(
                        secdonHandCardList.get(0),
                        secdonHandCardList.get(2),
                        secdonHandCardList.get(3))
                )
        )).thenReturn(comparisonResult);

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(comparisonResult);
        verify(highCardComparator).compareCardLists(any(), any());
    }
}