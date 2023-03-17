package comparators;

import model.Hand;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.sortedCardListByRank;

public class HighCardComparator implements HandComparator {

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstSortedCardList = sortedCardListByRank(firstHand);
        var secondSortedCardList = sortedCardListByRank(secondHand);

        for (var index = 0; index < firstSortedCardList.size(); index++) {
            var firstCardRank = firstSortedCardList.get(index).getValue().getRank();
            var secondCardRank = secondSortedCardList.get(index).getValue().getRank();

            if (firstCardRank > secondCardRank) {
                return FIRST_HAND_WIN;
            }
            if (secondCardRank > firstCardRank) {
                return SECOND_HAND_WIN;
            }
        }

        return TIE;
    }

}
