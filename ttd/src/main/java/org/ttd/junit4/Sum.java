package org.ttd.junit4;

public class Sum implements Expression {

    Expression augment;
    Expression addend;

    public Sum(Expression augment, Expression addend) {
        this.augment = augment;
        this.addend = addend;
    }

    @Override
    public Money reduce(Bank bank, String to) {
        int amount = augment.reduce(bank, to).amount + addend.reduce(bank, to).amount;
        return new Money(amount, to);
    }

    @Override
    public Expression plus(Expression added) {
        return new Sum(this, addend);
    }

    @Override
    public Expression times(int multiplier) {
        return new Sum(augment.times(multiplier), addend.times(multiplier));
    }
}
