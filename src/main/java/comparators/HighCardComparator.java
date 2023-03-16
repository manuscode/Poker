package comparators;

import model.Hand;

import static comparators.ComparisonResult.TIE;

public class HighCardComparator implements HandComparator {

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        return TIE;
    }
}
