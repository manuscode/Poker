package model;

import org.junit.jupiter.api.Test;

import static model.CardSuit.*;
import static model.CardValue.*;
import static model.Hand.createHand;
import static org.assertj.core.api.Assertions.assertThat;

class HandTest {

    @Test
    void createHand_shouldReturnHand_whenFiveCardsSupplied() {
        var card1 = new Card(CLUBS, JACK);
        var card2 = new Card(SPADES, KING);
        var card3 = new Card(HEARTS, TWO);
        var card4 = new Card(CLUBS, SEVEN);
        var card5 = new Card(DIAMONDS, TEN);

        var result = createHand(card1, card2, card3, card4, card5);

        assertThat(result.getCardList()).containsExactly(card1, card2, card3, card4, card5);
    }
}