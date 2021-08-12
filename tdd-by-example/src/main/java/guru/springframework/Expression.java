package guru.springframework;

public interface Expression {

    Money reduce(Bank bank, String toCurrency);

    Expression times(int multiplier);

    Expression plus(Expression amount);
}
