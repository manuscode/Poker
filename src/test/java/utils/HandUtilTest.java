package utils;

import model.Card;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;

class HandUtilTest {

    @Test
    void getCardsExceptOfValue() {
        var card1 = new Card(CLUBS, ACE);
        var card2 = new Card(SPADES, TEN);
        var card3 = new Card(DIAMONDS, JACK);
        var card4 = new Card(HEARTS, SEVEN);
        var card5 = new Card(HEARTS, TWO);
        var hand = createHand(card1, card2, card3, card4, card5);

        var result = HandUtil.getCardsExceptOfValue(hand, TEN);

        assertThat(result).isEqualTo(List.of(card1, card3, card4, card5));
    }

    @Test
    void getCardsExceptOfValues() {
        var card1 = new Card(CLUBS, ACE);
        var card2 = new Card(SPADES, TEN);
        var card3 = new Card(DIAMONDS, JACK);
        var card4 = new Card(HEARTS, SEVEN);
        var card5 = new Card(HEARTS, TWO);
        var hand = createHand(card1, card2, card3, card4, card5);

        var result = HandUtil.getCardsExceptOfValues(hand, List.of(TEN, JACK, TWO));

        assertThat(result).isEqualTo(List.of(card1, card4));
    }

    @Test
    void getCardsByValue() {
        var card1 = new Card(CLUBS, ACE);
        var card2 = new Card(SPADES, ACE);
        var card3 = new Card(DIAMONDS, JACK);
        var card4 = new Card(HEARTS, JACK);
        var card5 = new Card(HEARTS, TWO);
        var hand = createHand(card1, card2, card3, card4, card5);

        var result = HandUtil.getCardsByValue(hand);

        var expectedMap = Map.of(
                ACE, List.of(card1, card2),
                JACK, List.of(card3, card4),
                TWO, List.of(card5)
        );
        assertThat(result).isEqualTo(expectedMap);
    }

    @Test
    void getCardsBySuit() {
        var card1 = new Card(CLUBS, ACE);
        var card2 = new Card(DIAMONDS, ACE);
        var card3 = new Card(DIAMONDS, JACK);
        var card4 = new Card(HEARTS, JACK);
        var card5 = new Card(HEARTS, TWO);
        var hand = createHand(card1, card2, card3, card4, card5);

        var result = HandUtil.getCardsBySuit(hand);

        var expectedMap = Map.of(
                CLUBS, List.of(card1),
                DIAMONDS, List.of(card2, card3),
                HEARTS, List.of(card4, card5)
        );
        assertThat(result).isEqualTo(expectedMap);
    }

    @Test
    void toValueList() {
        var card1 = new Card(CLUBS, ACE);
        var card2 = new Card(DIAMONDS, ACE);
        var card3 = new Card(DIAMONDS, JACK);
        var card4 = new Card(HEARTS, JACK);
        var card5 = new Card(HEARTS, TWO);

        var result = HandUtil.toValueList(List.of(card1, card2, card3, card4, card5));

        assertThat(result).isEqualTo(List.of(ACE, ACE, JACK, JACK, TWO));
    }

    @Test
    void sortedValueListByRank() {
        var card1 = new Card(CLUBS, THREE);
        var card2 = new Card(DIAMONDS, ACE);
        var card3 = new Card(DIAMONDS, JACK);
        var card4 = new Card(HEARTS, KING);
        var card5 = new Card(HEARTS, TWO);
        var hand = createHand(card1, card2, card3, card4, card5);

        var result = HandUtil.sortedValueListByRank(hand);

        assertThat(result).containsExactly(ACE, KING, JACK, THREE, TWO);
    }

    @Test
    void testSortedValueListByRank() {
        var result = HandUtil.sortedValueListByRank(List.of(FIVE, EIGHT, KING, JACK));

        assertThat(result).containsExactly(KING, JACK, EIGHT, FIVE);
    }
}