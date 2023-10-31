package edu.hw4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AnimalUtils {

    public static final int HUNDRED = 100;

    private AnimalUtils() {
    }

    public static List<Animal> sortAnimalsByHeight(List<Animal> animals) {
        if (animals == null) {
            return new ArrayList<>();
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::height))
            .toList();
    }

    public static List<Animal> getTopKSortedDescAnimalsByWeight(List<Animal> animals, int k) {
        if (animals == null || k < 1) {
            return new ArrayList<>();
        }
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .toList();
    }

    public static Map<Animal.Type, Long> getNumberOfAnimalsOfEachType(List<Animal> animals) {
        if (animals == null) {
            return new HashMap<>();
        }

        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()));
    }

    public static Animal getAnimalWithLongestName(List<Animal> animals) {
        if (animals == null) {
            return null;
        }
        return animals.stream()
            .max(Comparator.comparing(animal -> animal.name().length()))
            .orElse(null);
    }

    public static Animal.Sex getDominantSex(List<Animal> animals) {
        if (animals == null || animals.isEmpty()) {
            return null;
        }
        return animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()))
            .entrySet().stream()
            .max(Comparator.comparingLong(Map.Entry::getValue))
            .get().getKey();
    }

    public static Map<Animal.Type, Optional<Animal>> getHeaviestAnimalsOfEachType(List<Animal> animals) {
        if (animals == null) {
            return new HashMap<>();
        }

        return animals.stream()
            .collect(Collectors.groupingBy(Animal::type, Collectors.maxBy(Comparator.comparingInt(Animal::weight))));
    }

    public static Optional<Animal> getKthOldestAnimal(List<Animal> animals, int k) {
        if (animals == null || k < 1) {
            return Optional.empty();
        }
        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::age).reversed())
            .skip(k - 1)
            .findFirst();
    }

    public static Optional<Animal> getHeaviestAnimalLowerThanKCM(List<Animal> animals, int k) {
        if (animals == null) {
            return Optional.empty();
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .filter(animal -> animal.height() < k)
            .findFirst();
    }

    public static Integer getTotalNumberOfPaws(List<Animal> animals) {
        if (animals == null) {
            return 0;
        }
        return animals.stream()
            .mapToInt(Animal::paws)
            .sum();
    }

    public static List<Animal> getAnimalsWhoseAgeNotEqualsPaws(List<Animal> animals) {
        if (animals == null) {
            return new ArrayList<>();
        }

        return animals.stream()
            .filter(animal -> animal.age() != animal.paws())
            .toList();
    }

    public static List<Animal> getBitingAnimalsWithHeightMoreThan100(List<Animal> animals) {
        if (animals == null) {
            return new ArrayList<>();
        }

        return animals.stream()
            .filter(Animal::bites)
            .filter(animal -> animal.height() > HUNDRED)
            .toList();
    }

    public static Long getAnimalsNumberWhoseWeightMoreThanHeight(List<Animal> animals) {
        if (animals == null) {
            return 0L;
        }

        return animals.stream()
            .filter(animal -> animal.weight() > animal.height())
            .count();
    }

    public static List<Animal> getAnimalsWhoseNamesConsistOfMoreThanTwoWords(List<Animal> animals) {
        if (animals == null) {
            return new ArrayList<>();
        }

        return animals.stream()
            .filter(animal -> animal.name().split(" ").length > 2)
            .toList();
    }

    public static Boolean isThereADogTallerThanKCM(List<Animal> animals, int k) {
        if (animals == null) {
            return false;
        }

        return animals.stream()
            .filter(animal -> animal.type() == Animal.Type.DOG)
            .anyMatch(animal -> animal.height() > k);
    }

    public static Integer getWeightAnimalsWhoseHeightBetweenKAndI(List<Animal> animals, int k, int l) {
        if (animals == null) {
            return 0;
        }

        return animals.stream()
            .filter(animal -> animal.height() > k)
            .filter(animal -> animal.height() < l)
            .mapToInt(Animal::weight)
            .sum();
    }

    public static List<Animal> getAnimalsSortedByTypeThenBySexThenByName(List<Animal> animals) {
        if (animals == null) {
            return new ArrayList<>();
        }

        return animals.stream()
            .sorted(Comparator.comparing(Animal::type).thenComparing(Animal::sex).thenComparing(Animal::name))
            .toList();
    }

    public static Boolean isSpidersBiteMoreOftenThanDogs(List<Animal> animals) {
        if (animals == null || animals.isEmpty()) {
            return false;
        }

        return animals.stream()
            .filter(Animal::bites)
            .filter(animal -> animal.type() == Animal.Type.DOG || animal.type() == Animal.Type.SPIDER)
            .collect(Collectors.groupingBy(Animal::type, Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .stream()
            .allMatch(typeLongEntry -> typeLongEntry.getKey() == Animal.Type.SPIDER);
    }

    public static Optional<Animal> getHeaviestFishFromLists(List<Animal>... animals) {
        if (animals == null) {
            return Optional.empty();
        }

        return Arrays.stream(animals)
            .flatMap(Collection::stream)
            .filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight));
    }

    public static Map<String, Set<ValidationError>> getAnimalsWithErrorsInRecord(List<Animal> animals) {
        if (animals == null) {
            return new HashMap<>();
        }

        return animals.stream()
            .collect(Collectors.toMap(Animal::name, Animal::getValidationErrors))
            .entrySet().stream()
            .filter(stringSetEntry -> !stringSetEntry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<String, String> getReadableAnimalsWithErrorsRecord(List<Animal> animals) {
        if (animals == null) {
            return new HashMap<>();
        }

        return animals.stream()
            .collect(Collectors.toMap(Animal::name, Animal::getValidationErrors))
            .entrySet().stream()
            .filter(e -> !e.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
    }
}
