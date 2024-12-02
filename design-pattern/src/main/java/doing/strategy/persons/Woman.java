package doing.strategy.persons;

import doing.strategy.Interest;
import doing.strategy.Person;

public class Woman implements Person {
    Interest interest;

    public Woman(Interest interest) {
        this.interest = interest;
    }

    @Override
    public void showInterest() {
        interest.show();
    }
}

