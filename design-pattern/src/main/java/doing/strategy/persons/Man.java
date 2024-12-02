package doing.strategy.persons;

import doing.strategy.Interest;
import doing.strategy.Person;

public class Man implements Person {
    Interest interest;

    public Man(Interest interest) {
        this.interest = interest;
    }

    @Override
    public void showInterest() {
        interest.show();
    }
}