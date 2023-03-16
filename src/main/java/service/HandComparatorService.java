package service;

import comparators.ComparisonResult;
import comparators.HandComparator;
import lombok.AllArgsConstructor;
import model.Hand;

import java.util.List;

import static comparators.ComparisonResult.TIE;

@AllArgsConstructor
public class HandComparatorService {

    private final List<HandComparator> orderedHandComparatorList;

    public ComparisonResult compareHands(Hand firstHand, Hand secondHand) {

        for (HandComparator handComparator : orderedHandComparatorList) {
            var comparisonResult = handComparator.compare(firstHand, secondHand);
            if (comparisonResult != TIE) {
                return comparisonResult;
            }
        }

        return TIE;
    }
}
