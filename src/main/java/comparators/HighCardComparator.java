package comparators;

import model.Card;
import model.CardValue;
import model.Hand;

import java.util.List;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.sortedValueListByRank;
import static utils.HandUtil.toValueList;

public class HighCardComparator implements HandComparator {

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        return compareCardLists(firstHand.getCardList(), secondHand.getCardList());
    }

    public ComparisonResult compareCardLists(List<Card> firstHandCardList, List<Card> secondHandCardList) {
        var firstHandValueList = toValueList(firstHandCardList);
        var secondHandValueList = toValueList(secondHandCardList);

        return compareValueLists(firstHandValueList, secondHandValueList);
    }

    public ComparisonResult compareValues(CardValue firstValue, CardValue secondValue) {
        var firstHandSortedValueList = List.of(firstValue);
        var secondHandSortedValueList = List.of(secondValue);

        return compareSortedValueLists(firstHandSortedValueList, secondHandSortedValueList);
    }

    public ComparisonResult compareValueLists(List<CardValue> firstHandValueList, List<CardValue> secondHandValueList) {
        var firstHandSortedValueList = sortedValueListByRank(firstHandValueList);
        var secondHandSortedValueList = sortedValueListByRank(secondHandValueList);

        return compareSortedValueLists(firstHandSortedValueList, secondHandSortedValueList);
    }

    private ComparisonResult compareSortedValueLists(
            List<CardValue> firstHandSortedValueList,
            List<CardValue> secondHandSortedValueList
    ) {
        for (var index = 0; index < firstHandSortedValueList.size(); index++) {
            var firstCardRank = firstHandSortedValueList.get(index).getRank();
            var secondCardRank = secondHandSortedValueList.get(index).getRank();

            if (firstCardRank > secondCardRank) {
                return FIRST_HAND_WIN;
            }
            if (secondCardRank > firstCardRank) {
                return SECOND_HAND_WIN;
            }
        }

        return DRAW;
    }
}
