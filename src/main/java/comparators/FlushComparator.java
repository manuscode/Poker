package comparators;

import lombok.AllArgsConstructor;
import model.Hand;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.getCardsBySuit;

@AllArgsConstructor
public class FlushComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandIsFlush = allCardsHaveSameSuit(firstHand);
        var secondHandIsFlush = allCardsHaveSameSuit(secondHand);

        if (firstHandIsFlush && secondHandIsFlush) {
            return highCardComparator.compare(firstHand, secondHand);
        }
        if (firstHandIsFlush) {
            return FIRST_HAND_WIN;
        }
        if (secondHandIsFlush) {
            return SECOND_HAND_WIN;
        }

        return DRAW;
    }

    public static boolean allCardsHaveSameSuit(Hand hand) {
        return getCardsBySuit(hand).size() == 1;
    }
}
