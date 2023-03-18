package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.Map;
import java.util.Optional;

import static comparators.ComparisonResult.*;
import static utils.HandUtil.getCardsByValue;

@AllArgsConstructor
public class ThreeOfAKindComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandThreeOfAKindValue = extractThreeOfAKindValue(firstHand);
        var secondHandThreeOfAKindValue = extractThreeOfAKindValue(secondHand);

        var firstHandHasThreeOfAKind = firstHandThreeOfAKindValue.isPresent();
        var secondHandHasThreeOfAKind = secondHandThreeOfAKindValue.isPresent();

        if (firstHandHasThreeOfAKind && secondHandHasThreeOfAKind) {
            return highCardComparator.compareValues(
                    firstHandThreeOfAKindValue.get(),
                    secondHandThreeOfAKindValue.get()
            );
        }
        if (firstHandHasThreeOfAKind) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasThreeOfAKind) {
            return SECOND_HAND_WIN;
        }

        return NO_MATCH;
    }

    private static Optional<CardValue> extractThreeOfAKindValue(Hand hand) {
        return getCardsByValue(hand).entrySet().stream()
                .filter(entry -> entry.getValue().size() == 3)
                .findFirst()
                .map(Map.Entry::getKey);
    }
}
