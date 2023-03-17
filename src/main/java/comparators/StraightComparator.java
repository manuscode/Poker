package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.Optional;

import static comparators.ComparisonResult.*;
import static model.CardValue.ACE;
import static model.CardValue.FIVE;
import static utils.HandUtil.getCardsByValue;
import static utils.HandUtil.sortedValueListByRank;

@AllArgsConstructor
public class StraightComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandStraightValue = extractStraightValue(firstHand);
        var secondHandStraightValue = extractStraightValue(secondHand);

        var firstHandHasStraight = firstHandStraightValue.isPresent();
        var secondHandHasStraight = secondHandStraightValue.isPresent();

        if (firstHandHasStraight && secondHandHasStraight) {
            return highCardComparator.compareValues(firstHandStraightValue.get(), secondHandStraightValue.get());
        }
        if (firstHandHasStraight) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasStraight) {
            return SECOND_HAND_WIN;
        }

        return DRAW;
    }

    public static Optional<CardValue> extractStraightValue(Hand hand) {
        var cardsByValue = getCardsByValue(hand);

        if (cardsByValue.size() != 5) {
            return Optional.empty();
        }

        var sortedValueList = sortedValueListByRank(hand);
        var highestValue = sortedValueList.get(0);
        var lowestValue = sortedValueList.get(4);

        if (highestValue.getRank() - lowestValue.getRank() == 4) {
            return Optional.of(highestValue);
        }
        if (highestValue == ACE && sortedValueList.get(1) == FIVE) {
            return Optional.of(FIVE);
        }

        return Optional.empty();
    }
}
