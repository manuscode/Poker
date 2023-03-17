package utils;

import model.Card;
import model.CardSuit;
import model.CardValue;
import model.Hand;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandUtil {

    public static List<Card> getCardsExceptOfValue(Hand hand, CardValue cardValue) {
        return hand.getCardList().stream()
                .filter(card -> card.getValue() != cardValue)
                .toList();
    }

    public static Map<CardValue, List<Card>> getCardsByValue(Hand hand) {
        return hand.getCardList().stream()
                .collect(Collectors.groupingBy(Card::getValue));
    }

    public static boolean allCardsHaveSameSuit(Hand hand) {
        return getCardsBySuit(hand).size() == 1;
    }

    private static Map<CardSuit, List<Card>> getCardsBySuit(Hand hand) {
        return hand.getCardList().stream()
                .collect(Collectors.groupingBy(Card::getSuit));
    }

    public static List<Card> sortedCardListByRank(List<Card> cardList) {
        return cardList.stream()
                .sorted(Comparator.<Card>comparingInt(card -> card.getValue().getRank()).reversed())
                .toList();
    }

}
