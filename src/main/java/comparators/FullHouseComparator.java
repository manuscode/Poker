package comparators;

import lombok.AllArgsConstructor;
import model.CardValue;
import model.Hand;

import java.util.Map;
import java.util.Optional;

import static comparators.ComparisonResult.*;
import static java.util.stream.Collectors.toMap;
import static utils.HandUtil.getCardsByValue;

@AllArgsConstructor
public class FullHouseComparator implements HandComparator {

    private final HighCardComparator highCardComparator;

    @Override
    public ComparisonResult compare(Hand firstHand, Hand secondHand) {
        var firstHandFullHouseValue = extractFullHouseValue(firstHand);
        var secondHandFullHouseValue = extractFullHouseValue(secondHand);

        var firstHandHasFullHouse = firstHandFullHouseValue.isPresent();
        var secondHandHasFullHouse = secondHandFullHouseValue.isPresent();

        if (firstHandHasFullHouse && secondHandHasFullHouse) {
            return highCardComparator.compareValues(
                    firstHandFullHouseValue.get(),
                    secondHandFullHouseValue.get()
            );
        }
        if (firstHandHasFullHouse) {
            return FIRST_HAND_WIN;
        }
        if (secondHandHasFullHouse) {
            return SECOND_HAND_WIN;
        }

        return NO_MATCH;
    }

    private Optional<CardValue> extractFullHouseValue(Hand hand) {
        var cardsByValueMap = getCardsByValue(hand);

        if (cardsByValueMap.size() != 2) {
            return Optional.empty();
        }

        var valueBySizeMap = cardsByValueMap.entrySet().stream().collect(toMap(
                entry -> entry.getValue().size(),
                Map.Entry::getKey
        ));

        var threeOfAKindValue = valueBySizeMap.get(3);

        return Optional.of(threeOfAKindValue);
    }

}
