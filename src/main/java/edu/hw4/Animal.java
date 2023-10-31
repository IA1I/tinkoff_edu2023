package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {

    private static final String NAME_PATTERN = "^[a-zA-Z ]{3,}$";
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

    public Set<ValidationError> getValidationErrors() {
        Set<ValidationError> validationErrors = new HashSet<>();
        validationErrors.add(getNameError());
        validationErrors.add(getTypeError());
        validationErrors.add(getSexError());
        validationErrors.add(getAgeError());
        validationErrors.add(getHeightError());
        validationErrors.add(getWeightError());
        validationErrors.remove(null);

        return validationErrors;
    }

    private ValidationError getNameError() {
        if (name == null) {
            return ValidationError.NAME_IS_NULL;
        } else if (name.isEmpty()) {
            return ValidationError.EMPTY_NAME;
        } else if (!name.matches(NAME_PATTERN)) {
            return ValidationError.WRONG_NAME;
        }

        return null;
    }

    private ValidationError getTypeError() {
        if (type == null) {
            return ValidationError.TYPE_IS_NULL;
        }

        return null;
    }

    private ValidationError getSexError() {
        if (sex == null) {
            return ValidationError.SEX_IS_NULL;
        }

        return null;
    }

    private ValidationError getAgeError() {
        if (age <= 0) {
            return ValidationError.WRONG_AGE;
        }

        return null;
    }

    private ValidationError getHeightError() {
        if (height <= 0) {
            return ValidationError.WRONG_HEIGHT;
        }

        return null;
    }

    private ValidationError getWeightError() {
        if (weight <= 0) {
            return ValidationError.WRONG_WEIGHT;
        }

        return null;
    }
}
