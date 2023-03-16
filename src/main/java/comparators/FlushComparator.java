package comparators;

import lombok.AllArgsConstructor;
import model.Hand;

import static utils.HandUtil.getCardsBySuit;

@AllArgsConstructor
public class FlushComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public int compare(Hand firstHand, Hand secondHand) {
        var firstHandCardsBySuitMap = getCardsBySuit(firstHand);
        var secondHandCardsBySuitMap = getCardsBySuit(secondHand);

        var firstHandIsFlush = firstHandCardsBySuitMap.size() == 1;
        var secondHandIsFlush = secondHandCardsBySuitMap.size() == 1;

        if(firstHandIsFlush && secondHandIsFlush) {
            return highCardComparator.compare(firstHand, secondHand);
        }
        if (firstHandIsFlush) {
            return 1;
        }
        if (secondHandIsFlush) {
            return -1;
        }

        return 0;
    }
}
