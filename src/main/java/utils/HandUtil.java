package utils;

import model.Card;
import model.CardSuit;
import model.CardValue;
import model.Hand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class HandUtil {

    public static List<Card> getCardsExceptOfValue(Hand hand, CardValue cardValue) {
        return getCardsExceptOfValues(hand, List.of(cardValue));
    }

    public static List<Card> getCardsExceptOfValues(Hand hand, List<CardValue> cardValueList) {
        return hand.getCardList().stream()
                .filter(card -> !cardValueList.contains(card.getValue()))
                .toList();
    }

    public static Map<CardValue, List<Card>> getCardsByValue(Hand hand) {
        return hand.getCardList().stream()
                .collect(Collectors.groupingBy(Card::getValue));
    }

    public static Map<CardSuit, List<Card>> getCardsBySuit(Hand hand) {
        return hand.getCardList().stream()
                .collect(Collectors.groupingBy(Card::getSuit));
    }

    public static List<CardValue> toValueList(List<Card> cardList) {
        return cardList.stream()
                .map(Card::getValue)
                .toList();
    }

    public static List<CardValue> sortedValueListByRank(Hand hand) {
        return sortedValueListByRank(hand.getCardList().stream()
                .map(Card::getValue)
                .toList());
    }

    public static List<CardValue> sortedValueListByRank(List<CardValue> cardValueList) {
        return cardValueList.stream()
                .sorted(comparingInt(CardValue::getRank).reversed())
                .toList();
    }

}
