package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public class ErrorUtils {
    private static final String NAME_PATTERN = "^[a-zA-Z ]{3,}$";

    private ErrorUtils() {
    }

    public static Set<ValidationError> getValidationErrors(Animal animal) {
        Set<ValidationError> validationErrors = new HashSet<>();
        validationErrors.add(getNameError(animal.name()));
        validationErrors.add(getTypeError(animal.type()));
        validationErrors.add(getSexError(animal.sex()));
        validationErrors.add(getAgeError(animal.age()));
        validationErrors.add(getHeightError(animal.height()));
        validationErrors.add(getWeightError(animal.weight()));
        validationErrors.remove(null);

        return validationErrors;
    }

    private static ValidationError getNameError(String name) {
        if (name == null) {
            return ValidationError.NAME_IS_NULL;
        } else if (name.isEmpty()) {
            return ValidationError.EMPTY_NAME;
        } else if (!name.matches(NAME_PATTERN)) {
            return ValidationError.WRONG_NAME;
        }

        return null;
    }

    private static ValidationError getTypeError(Animal.Type type) {
        if (type == null) {
            return ValidationError.TYPE_IS_NULL;
        }

        return null;
    }

    private static ValidationError getSexError(Animal.Sex sex) {
        if (sex == null) {
            return ValidationError.SEX_IS_NULL;
        }

        return null;
    }

    private static ValidationError getAgeError(int age) {
        if (age <= 0) {
            return ValidationError.WRONG_AGE;
        }

        return null;
    }

    private static ValidationError getHeightError(int height) {
        if (height <= 0) {
            return ValidationError.WRONG_HEIGHT;
        }

        return null;
    }

    private static ValidationError getWeightError(int weight) {
        if (weight <= 0) {
            return ValidationError.WRONG_WEIGHT;
        }

        return null;
    }
}
