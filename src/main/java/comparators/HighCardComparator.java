package comparators;

import model.Card;
import model.Hand;

import java.util.List;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.sortedCardListByRank;

public class HighCardComparator implements HandComparator {

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        return compareCardLists(firstHand.getCardList(), secondHand.getCardList());
    }

    public ComparisonResult compareCardLists(List<Card> firstHandCardList, List<Card> secondHandCardList) {
        var firstHandSortedCardList = sortedCardListByRank(firstHandCardList);
        var secondHandSortedCardList = sortedCardListByRank(secondHandCardList);

        for (var index = 0; index < firstHandSortedCardList.size(); index++) {
            var firstCardRank = firstHandSortedCardList.get(index).getValue().getRank();
            var secondCardRank = secondHandSortedCardList.get(index).getValue().getRank();

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
