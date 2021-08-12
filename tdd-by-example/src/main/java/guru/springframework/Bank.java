package guru.springframework;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Bank {

    private final Map<Pair, Integer> rateMap = new HashMap<>();

    Money reduce(Expression source, String toCurrency) {
        return source.reduce(this, toCurrency);
    }

    int rate(String from, String to) {
        return Objects.equals(from, to) ? 1 : rateMap.get(new Pair(from, to));
    }

    void addRate(String from, String to, int rate) {
        rateMap.put(new Pair(from, to), rate);
        rateMap.put(new Pair(to, from), 1 / rate);
    }
}
