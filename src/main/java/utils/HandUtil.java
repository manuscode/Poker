package utils;

import model.Card;
import model.CardSuit;
import model.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandUtil {

    public static boolean allCardsHaveSameSuit(Hand hand) {
        return getCardsBySuit(hand).size() == 1;
    }

    private static Map<CardSuit, List<Card>> getCardsBySuit(Hand hand) {
        return hand.getCardList().stream()
                .collect(Collectors.groupingBy(Card::getSuit));
    }

    public static List<Card> sortedCardListByRank(Hand hand) {
        return hand.getCardList().stream()
                .sorted(Comparator.<Card>comparingInt(card -> card.getValue().getRank()).reversed())
                .toList();
    }

}
