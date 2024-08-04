package pattern23.strategy;

public interface Person {
    void showInterest();

}

class Woman implements Person {
    Interest interest;

    public Woman(Interest interest) {
        this.interest = interest;
    }

    @Override
    public void showInterest() {
        interest.show();
    }
}

class Man implements Person {
    Interest interest;

    public Man(Interest interest) {
        this.interest = interest;
    }

    @Override
    public void showInterest() {
        interest.show();
    }
}

