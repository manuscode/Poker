package comparators;

import model.Card;
import org.junit.jupiter.api.Test;

import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;

class FlushComparatorTest {

    private final FlushComparator testee = new FlushComparator();

    @Test
    void compare_shouldReturnTie_whenBothHandsAreNotFlush() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(SPADES, TEN),
                new Card(DIAMONDS, JACK),
                new Card(HEARTS, SEVEN),
                new Card(HEARTS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, JACK),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, EIGHT),
                new Card(SPADES, QUEEN)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(0);
    }

    @Test
    void compare_shouldReturnWinForFirstHand_whenOnlyFirstHandIsFlush() {
        var firstHand = createHand(
                new Card(CLUBS, ACE),
                new Card(CLUBS, TEN),
                new Card(CLUBS, JACK),
                new Card(CLUBS, SEVEN),
                new Card(CLUBS, JACK)
        );
        var secondHand = createHand(
                new Card(DIAMONDS, TWO),
                new Card(SPADES, JACK),
                new Card(HEARTS, SIX),
                new Card(DIAMONDS, EIGHT),
                new Card(SPADES, QUEEN)
        );

        var result = testee.compare(firstHand, secondHand);

        assertThat(result).isEqualTo(1);
    }
}