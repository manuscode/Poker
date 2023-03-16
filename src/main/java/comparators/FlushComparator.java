package comparators;

import model.Hand;

import static utils.HandUtil.getCardsBySuit;

public class FlushComparator implements HandComparator {

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        var firstHandCardsBySuitsMap = getCardsBySuit(firstHand);

        if (firstHandCardsBySuitsMap.size() == 1) {
            return 1;
        }

        return 0;
    }
}
