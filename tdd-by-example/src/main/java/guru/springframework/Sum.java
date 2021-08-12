package guru.springframework;

class Sum implements Expression {

    final Expression augmend;
    final Expression addmend;

    Sum(Expression augmend, Expression addmend) {
        this.augmend = augmend;
        this.addmend = addmend;
    }

    @Override
    public Money reduce(Bank bank, String toCurrency) {
        int amount = this.augmend.reduce(bank, toCurrency).amount + this.addmend.reduce(bank, toCurrency).amount;
        return new Money(amount, toCurrency);
    }

    @Override
    public Expression times(int multiplier) {
        return new Sum(augmend.times(multiplier), addmend.times(multiplier));
    }

    @Override
    public Expression plus(Expression addmend) {
        return new Sum(this, addmend);
    }
}
