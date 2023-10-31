package edu.hw2.Task1;

public sealed interface Expr {
    double evaluate();

    record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    record Negate(double value) implements Expr {
        public Negate(Expr expression) {
            this(-expression.evaluate());
        }

        @Override
        public double evaluate() {
            return value;
        }
    }

    record Exponent(double value) implements Expr {
        public Exponent(Expr base, Expr power) {
            this(Math.pow(base.evaluate(), power.evaluate()));
        }

        public Exponent(Expr base, double power) {
            this(base, new Constant(power));
        }

        public Exponent(double base, Expr power) {
            this(new Constant(base), power);
        }

        public Exponent(double base, double power) {
            this(new Constant(base), new Constant(power));
        }

        @Override
        public double evaluate() {
            return value;
        }
    }

    record Addition(double value) implements Expr {
        public Addition(Expr expression1, Expr expression2) {
            this(expression1.evaluate() + expression2.evaluate());
        }

        public Addition(double expression1, double expression2) {
            this(new Constant(expression1), new Constant(expression2));
        }

        public Addition(Expr expression1, double expression2) {
            this(expression1, new Constant(expression2));
        }

        public Addition(double expression1, Expr expression2) {
            this(new Constant(expression1), expression2);
        }

        @Override
        public double evaluate() {
            return value;
        }
    }

    record Multiplication(double value) implements Expr {
        public Multiplication(Expr expression1, Expr expression2) {
            this(expression1.evaluate() * expression2.evaluate());
        }

        public Multiplication(double expression1, double expression2) {
            this(new Constant(expression1), new Constant(expression2));
        }

        public Multiplication(Expr expression1, double expression2) {
            this(expression1, new Constant(expression2));
        }

        public Multiplication(double expression1, Expr expression2) {
            this(new Constant(expression1), expression2);
        }

        @Override
        public double evaluate() {
            return value;
        }
    }
}
