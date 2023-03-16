package model;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
public class Hand {

    List<Card> cardList;

    public static Hand createHand(
            @NonNull Card card1,
            @NonNull Card card2,
            @NonNull Card card3,
            @NonNull Card card4,
            @NonNull Card card5
    ) {
        return new Hand(List.of(card1, card2, card3, card4, card5));
    }
}
