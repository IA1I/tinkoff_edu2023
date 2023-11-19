package edu.project3;

public enum Argument {
    PATH("--path"),
    FROM("--from"),
    TO("--to"),
    FORMAT("--format");

    private String argument;

    Argument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public static Argument valueOfArgument(String argument) {
        for (Argument a : values()) {
            if (a.argument.equals(argument)) {
                return a;
            }
        }
        return null;
    }
}
