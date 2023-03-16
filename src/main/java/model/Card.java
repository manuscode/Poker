package model;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Card {

    CardSuit suit;
    CardValue value;

}
