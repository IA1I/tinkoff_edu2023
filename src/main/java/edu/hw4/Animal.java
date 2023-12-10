package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    public static final int PAWS_OF_CATS_AND_DOGS = 4;
    public static final int PAWS_OF_BIRD = 2;
    public static final int PAWS_OF_FISH = 0;
    public static final int PAWS_OF_SPIDER = 8;

    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    public int paws() {
        return switch (type) {
            case CAT, DOG -> PAWS_OF_CATS_AND_DOGS;
            case BIRD -> PAWS_OF_BIRD;
            case FISH -> PAWS_OF_FISH;
            case SPIDER -> PAWS_OF_SPIDER;
        };
    }
}
