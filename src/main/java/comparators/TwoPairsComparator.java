package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.List;
import java.util.Map.Entry;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.getCardsByValue;
import static utils.HandUtil.getCardsExceptOfValues;

@AllArgsConstructor
public class TwoPairsComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandPairValueList = getPairValueList(firstHand);
        var secondHandPairValueList = getPairValueList(secondHand);

        var firstHandHasTwoPairs = firstHandPairValueList.size() == 2;
        var secondHandHasTwoPairs = secondHandPairValueList.size() == 2;

        if (firstHandHasTwoPairs && secondHandHasTwoPairs) {
            return compareWhenBothHandsHaveTwoPairs(firstHand, secondHand, firstHandPairValueList, secondHandPairValueList);
        }
        if (firstHandHasTwoPairs) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasTwoPairs) {
            return SECOND_HAND_WIN;
        }

        return TIE;
    }

    private ComparisonResult compareWhenBothHandsHaveTwoPairs(
            Hand firstHand,
            Hand secondHand,
            List<CardValue> firstHandPairValueList,
            List<CardValue> secondHandPairValueList
    ) {
        var comparisonResult = highCardComparator.compareValueLists(firstHandPairValueList, secondHandPairValueList);

        if (comparisonResult != TIE) {
            return comparisonResult;
        }

        var firstHandRemainingCard = getCardsExceptOfValues(firstHand, firstHandPairValueList);
        var secondHandRemainingCard = getCardsExceptOfValues(secondHand, secondHandPairValueList);

        return highCardComparator.compareCardLists(firstHandRemainingCard, secondHandRemainingCard);
    }

    private static List<CardValue> getPairValueList(Hand firstHand) {
        return getCardsByValue(firstHand).entrySet().stream()
                .filter(entry -> entry.getValue().size() == 2)
                .map(Entry::getKey)
                .toList();
    }
}
