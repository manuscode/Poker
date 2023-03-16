package comparators;

import model.Hand;

public interface HandComparator {

    ComparisonResult compare(Hand firstHand, Hand secondHand);

}
