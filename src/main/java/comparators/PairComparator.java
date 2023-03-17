package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.Map;
import java.util.Optional;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.getCardsByValue;
import static utils.HandUtil.getCardsExceptOfValue;

@AllArgsConstructor
public class PairComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandPairValue = extractPairValue(firstHand);
        var secondHandPairValue = extractPairValue(secondHand);

        var firstHandHasPair = firstHandPairValue.isPresent();
        var secondHandHasPair = secondHandPairValue.isPresent();

        if (firstHandHasPair && secondHandHasPair) {
            return compareWhenBothHandsHavePair(firstHand, secondHand, firstHandPairValue.get(), secondHandPairValue.get());
        }
        if (firstHandHasPair) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasPair) {
            return SECOND_HAND_WIN;
        }

        return DRAW;
    }

    private ComparisonResult compareWhenBothHandsHavePair(
            Hand firstHand,
            Hand secondHand,
            CardValue firstHandPairValue,
            CardValue secondHandPairValue
    ) {
        var comparisonResult = highCardComparator.compareValues(firstHandPairValue, secondHandPairValue);

        if (comparisonResult != DRAW) {
            return comparisonResult;
        }

        var firstHandRemainingCards = getCardsExceptOfValue(firstHand, firstHandPairValue);
        var secondHandRemainingCards = getCardsExceptOfValue(secondHand, firstHandPairValue);

        return highCardComparator.compareCardLists(firstHandRemainingCards, secondHandRemainingCards);
    }

    private static Optional<CardValue> extractPairValue(Hand hand) {
        return getCardsByValue(hand).entrySet().stream()
                .filter(entry -> entry.getValue().size() == 2)
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
