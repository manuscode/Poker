package utils;

import model.Card;
import model.CardSuit;
import model.Hand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandUtil {

    public static Map<CardSuit, List<Card>> getCardsBySuit(Hand hand) {
        return hand.getCardList().stream()
                .collect(Collectors.groupingBy(Card::getSuit));
    }

}
