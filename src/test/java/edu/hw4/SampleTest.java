package edu.hw4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class SampleTest {
    public static final int INDEX_OF_HEAVIEST_DOG = 2;
    public static final int INDEX_OF_HEAVIEST_CAT = 6;
    public static final int INDEX_OF_HEAVIEST_BIRD = 8;
    public static final int INDEX_OF_HEAVIEST_FISH = 11;
    public static final int INDEX_OF_HEAVIEST_SPIDER = 12;
    public static final int INDEX_2TH_OLDEST_ANIMAL = 7;
    public static final int INDEX_OF_HEAVIEST_ANIMAL_LOWER_30_CM = 0;
    public static final int TOTAL_NUMBER_OF_PAWS = 48;
    public static final int INDEX_OF_ANIMAL_WHOSE_AGE_EQUALS_PAWS = 3;
    public static final int INDEX_OF_ANIMAL_WHOSE_BITING_WITH_HEIGHT_MORE_THAN_100 = 11;
    public static final int INDEX_OF_ANIMAL_WHOSE_NAMES_CONSIST_OF_MORE_THAN_TWO_WORDS = 1;
    static List<Animal> animals;
    static int size;

    @BeforeAll
    static void setup() {
        animals = new ArrayList<>();
        animals.add(new Animal("Max", Animal.Type.DOG, Animal.Sex.M, 1, 25, 14000, true));
        animals.add(new Animal(
            "Salvador Domingo Felipe Jacinto Dali i Domenech, Marquess of Dali of Pubol",
            Animal.Type.DOG,
            Animal.Sex.M,
            6,
            40,
            7500,
            true
        ));
        animals.add(new Animal("Helga", Animal.Type.DOG, Animal.Sex.F, 8, 50, 25000, true));
        animals.add(new Animal("Shaya", Animal.Type.DOG, Animal.Sex.F, 4, 20, 4500, true));
        animals.add(new Animal("Vega", Animal.Type.CAT, Animal.Sex.F, 2, 23, 3000, true));
        animals.add(new Animal("Varya", Animal.Type.CAT, Animal.Sex.F, 17, 23, 2500, true));
        animals.add(new Animal("Mars", Animal.Type.CAT, Animal.Sex.M, 7, 25, 5000, true));
        animals.add(new Animal("Kesha", Animal.Type.BIRD, Animal.Sex.M, 25, 19, 42, true));
        animals.add(new Animal("Bird Govorun", Animal.Type.BIRD, Animal.Sex.M, 43, 20, 50, true));
        animals.add(new Animal("Nemo", Animal.Type.FISH, Animal.Sex.M, 2, 8, 200, false));
        animals.add(new Animal("Dory", Animal.Type.FISH, Animal.Sex.F, 6, 35, 1200, false));
        animals.add(new Animal("Dave", Animal.Type.FISH, Animal.Sex.M, 24, 150, 30333, true));
        animals.add(new Animal("Anatoly", Animal.Type.SPIDER, Animal.Sex.M, 10, 10, 85, true));
        animals.add(new Animal("Natasha", Animal.Type.SPIDER, Animal.Sex.F, 2, 2, 1, true));
        size = animals.size();
    }

    @Nested
    @DisplayName("Задача 1")
    class TestTask1 {

        @Test
        void shouldReturnSortedByHeightListAnimals() {
            List<Animal> expected = getCopyAnimals();
            expected.sort(Comparator.comparingInt(Animal::height));

            List<Animal> actual = AnimalUtils.sortAnimalsByHeight(animals);

            assertThat(actual).containsExactlyElementsOf(expected);
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            List<Animal> actual = AnimalUtils.sortAnimalsByHeight(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNull() {
            List<Animal> actual = AnimalUtils.sortAnimalsByHeight(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 2")
    class TestTask2 {
        @Test
        void shouldReturnTop4SortedByWeightListAnimals() {
            List<Animal> expected = getCopyAnimals();
            expected.sort(Comparator.comparingInt(Animal::weight).reversed());
            expected = List.of(expected.get(0), expected.get(1), expected.get(2), expected.get(3));

            List<Animal> actual = AnimalUtils.getTopKSortedDescAnimalsByWeight(animals, 4);

            assertThat(actual).containsExactlyElementsOf(expected);
        }

        @Test
        void shouldReturnAllAnimalsSortedByWeightListAnimalsWhenKMoreThenSize() {
            List<Animal> expected = getCopyAnimals();
            expected.sort(Comparator.comparingInt(Animal::weight).reversed());

            List<Animal> actual = AnimalUtils.getTopKSortedDescAnimalsByWeight(animals, 15);

            assertThat(actual).containsExactlyElementsOf(expected);
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            List<Animal> actual = AnimalUtils.getTopKSortedDescAnimalsByWeight(new ArrayList<>(), 2);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNullList() {
            List<Animal> actual = AnimalUtils.getTopKSortedDescAnimalsByWeight(null, 4);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNegativeK() {
            List<Animal> actual = AnimalUtils.getTopKSortedDescAnimalsByWeight(animals, -3);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 3")
    class TestTask3 {
        @Test
        void shouldReturnMapWihNumberOfAnimalsOfEachType() {
            Map<Animal.Type, Long> expected = new HashMap<>();
            expected.put(Animal.Type.DOG, 4L);
            expected.put(Animal.Type.CAT, 3L);
            expected.put(Animal.Type.BIRD, 2L);
            expected.put(Animal.Type.FISH, 3L);
            expected.put(Animal.Type.SPIDER, 2L);
            Map<Animal.Type, Long> actual = AnimalUtils.getNumberOfAnimalsOfEachType(animals);

            assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
        }

        @Test
        void shouldReturnEmptyMapForEmptyList() {
            Map<Animal.Type, Long> actual = AnimalUtils.getNumberOfAnimalsOfEachType(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyMapForNullList() {
            Map<Animal.Type, Long> actual = AnimalUtils.getNumberOfAnimalsOfEachType(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 4")
    class TestTask4 {

        @Test
        void shouldReturnAnimalWithLongestName() {
            Animal expected = animals.get(1);
            Animal actual = AnimalUtils.getAnimalWithLongestName(animals);

            assertThat(actual).extracting(
                Animal::name,
                Animal::type,
                Animal::sex,
                Animal::age,
                Animal::height,
                Animal::weight,
                Animal::bites
            ).containsExactly(
                expected.name(),
                expected.type(),
                expected.sex(),
                expected.age(),
                expected.height(),
                expected.weight(),
                expected.bites()
            );
        }

        @Test
        void shouldReturnNullForEmptyList() {
            Animal actual = AnimalUtils.getAnimalWithLongestName(new ArrayList<>());

            assertThat(actual).isNull();
        }

        @Test
        void shouldReturnNullForNull() {
            Animal actual = AnimalUtils.getAnimalWithLongestName(null);

            assertThat(actual).isNull();
        }
    }

    @Nested
    @DisplayName("Задача 5")
    class TestTask5 {
        @Test
        void shouldReturnDominantSex() {
            Animal.Sex actual = AnimalUtils.getDominantSex(animals);
            Animal.Sex expected = Animal.Sex.M;

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldReturnNullForNullList() {
            Animal.Sex actual = AnimalUtils.getDominantSex(null);

            assertThat(actual).isNull();
        }

        @Test
        void shouldReturnNullForEmptyList() {
            Animal.Sex actual = AnimalUtils.getDominantSex(new ArrayList<>());

            assertThat(actual).isNull();
        }
    }

    @Nested
    @DisplayName("Задача 6")
    class TestTask6 {

        @Test
        void shouldReturnMapHeaviestAnimalsOfEachType() {
            Map<Animal.Type, Optional<Animal>> expected = new HashMap<>();
            expected.put(Animal.Type.DOG, Optional.of(animals.get(INDEX_OF_HEAVIEST_DOG)));
            expected.put(Animal.Type.CAT, Optional.of(animals.get(INDEX_OF_HEAVIEST_CAT)));
            expected.put(Animal.Type.BIRD, Optional.of(animals.get(INDEX_OF_HEAVIEST_BIRD)));
            expected.put(Animal.Type.FISH, Optional.of(animals.get(INDEX_OF_HEAVIEST_FISH)));
            expected.put(Animal.Type.SPIDER, Optional.of(animals.get(INDEX_OF_HEAVIEST_SPIDER)));
            Map<Animal.Type, Optional<Animal>> actual = AnimalUtils.getHeaviestAnimalsOfEachType(animals);

            assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
        }

        @Test
        void shouldReturnEmptyMapForEmptyList() {
            Map<Animal.Type, Optional<Animal>> actual = AnimalUtils.getHeaviestAnimalsOfEachType(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyMapForNullList() {
            Map<Animal.Type, Optional<Animal>> actual = AnimalUtils.getHeaviestAnimalsOfEachType(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 7")
    class TestTask7 {
        @Test
        void shouldReturn2thOldestAnimal() {
            Animal expected = animals.get(INDEX_2TH_OLDEST_ANIMAL);
            Optional<Animal> actual = AnimalUtils.getKthOldestAnimal(animals, 2);

            assertThat(actual.get()).extracting(
                Animal::name,
                Animal::type,
                Animal::sex,
                Animal::age,
                Animal::height,
                Animal::weight,
                Animal::bites
            ).containsExactly(
                expected.name(),
                expected.type(),
                expected.sex(),
                expected.age(),
                expected.height(),
                expected.weight(),
                expected.bites()
            );
        }

        @Test
        void shouldReturnEmptyOptionalForEmptyList() {
            Optional<Animal> actual = AnimalUtils.getKthOldestAnimal(new ArrayList<>(), 2);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyOptionalForNullList() {
            Optional<Animal> actual = AnimalUtils.getKthOldestAnimal(null, 2);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyOptionalForNegativeK() {
            Optional<Animal> actual = AnimalUtils.getKthOldestAnimal(animals, -2);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 8")
    class TestTask8 {
        @Test
        void shouldReturnHeaviestAnimalLower30CM() {
            Animal expected = animals.get(INDEX_OF_HEAVIEST_ANIMAL_LOWER_30_CM);
            Optional<Animal> actual = AnimalUtils.getHeaviestAnimalLowerThanKCM(animals, 30);

            assertThat(actual.get()).extracting(
                Animal::name,
                Animal::type,
                Animal::sex,
                Animal::age,
                Animal::height,
                Animal::weight,
                Animal::bites
            ).containsExactly(
                expected.name(),
                expected.type(),
                expected.sex(),
                expected.age(),
                expected.height(),
                expected.weight(),
                expected.bites()
            );
        }

        @Test
        void shouldReturnEmptyOptionalForEmptyList() {
            Optional<Animal> actual = AnimalUtils.getHeaviestAnimalLowerThanKCM(new ArrayList<>(), 30);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyOptionalForNullList() {
            Optional<Animal> actual = AnimalUtils.getHeaviestAnimalLowerThanKCM(null, 30);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyOptionalForNegativeK() {
            Optional<Animal> actual = AnimalUtils.getHeaviestAnimalLowerThanKCM(new ArrayList<>(), -100);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 9")
    class TestTask9 {
        @Test
        void shouldReturnTotalNumberOfPaws() {
            Integer expected = TOTAL_NUMBER_OF_PAWS;
            Integer actual = AnimalUtils.getTotalNumberOfPaws(animals);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldReturn0ForEmptyList() {
            Integer actual = AnimalUtils.getTotalNumberOfPaws(new ArrayList<>());

            assertThat(actual).isEqualTo(0);
        }

        @Test
        void shouldReturn0ForNullList() {
            Integer actual = AnimalUtils.getTotalNumberOfPaws(null);

            assertThat(actual).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Задача 10")
    class TestTask10 {
        @Test
        void shouldReturnListAnimalsWhoseAgeNotEqualsPaws() {
            List<Animal> expected = getCopyAnimals();
            expected.remove(INDEX_OF_ANIMAL_WHOSE_AGE_EQUALS_PAWS);
            List<Animal> actual = AnimalUtils.getAnimalsWhoseAgeNotEqualsPaws(animals);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            List<Animal> actual = AnimalUtils.getAnimalsWhoseAgeNotEqualsPaws(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNullList() {
            List<Animal> actual = AnimalUtils.getAnimalsWhoseAgeNotEqualsPaws(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 11")
    class TestTask11 {
        @Test
        void shouldReturnListAnimalsWhoseBitingWithHeightMoreThan100() {
            List<Animal> expected = List.of(animals.get(INDEX_OF_ANIMAL_WHOSE_BITING_WITH_HEIGHT_MORE_THAN_100));
            List<Animal> actual = AnimalUtils.getBitingAnimalsWithHeightMoreThan100(animals);

            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            List<Animal> actual = AnimalUtils.getBitingAnimalsWithHeightMoreThan100(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNullList() {
            List<Animal> actual = AnimalUtils.getBitingAnimalsWithHeightMoreThan100(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 12")
    class TestTask12 {
        @Test
        void shouldReturnListAnimalsWhoseBitingWithHeightMoreThan100() {
            Long expected = 13L;
            Long actual = AnimalUtils.getAnimalsNumberWhoseWeightMoreThanHeight(animals);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldReturnZeroForEmptyList() {
            Long actual = AnimalUtils.getAnimalsNumberWhoseWeightMoreThanHeight(new ArrayList<>());

            assertThat(actual).isEqualTo(0);
        }

        @Test
        void shouldReturnZeroForNullList() {
            Long actual = AnimalUtils.getAnimalsNumberWhoseWeightMoreThanHeight(null);

            assertThat(actual).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Задача 13")
    class TestTask13 {
        @Test
        void shouldReturnListAnimalsWhoseNamesConsistOfMoreThanTwoWords() {
            List<Animal> expected = List.of(animals.get(INDEX_OF_ANIMAL_WHOSE_NAMES_CONSIST_OF_MORE_THAN_TWO_WORDS));
            List<Animal> actual = AnimalUtils.getAnimalsWhoseNamesConsistOfMoreThanTwoWords(animals);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            List<Animal> actual = AnimalUtils.getAnimalsWhoseNamesConsistOfMoreThanTwoWords(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNullList() {
            List<Animal> actual = AnimalUtils.getAnimalsWhoseNamesConsistOfMoreThanTwoWords(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 14")
    class TestTask14 {
        @Test
        void shouldReturnFalseForK55() {
            Boolean actual = AnimalUtils.isThereADogTallerThanKCM(animals, 55);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnTrueForK15() {
            Boolean actual = AnimalUtils.isThereADogTallerThanKCM(animals, 15);

            assertThat(actual).isTrue();
        }

        @Test
        void shouldReturnFalseForEmptyList() {
            Boolean actual = AnimalUtils.isThereADogTallerThanKCM(new ArrayList<>(), 15);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnFalseForNullList() {
            Boolean actual = AnimalUtils.isThereADogTallerThanKCM(null, 15);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnFalseForList() {
            Boolean actual = AnimalUtils.isThereADogTallerThanKCM(List.of(new Animal(
                "Smt",
                Animal.Type.CAT,
                Animal.Sex.M,
                1,
                1,
                1,
                false
            )), 15);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("Задача 15")
    class TestTask15 {
        @Test
        void shouldReturnWeightAnimalsWhoseHeightBetween5And30() {
            Map<Animal.Type, Integer> expected = new HashMap<>();
            expected.put(Animal.Type.DOG, 18500);
            expected.put(Animal.Type.FISH, 200);
            expected.put(Animal.Type.BIRD, 92);
            expected.put(Animal.Type.SPIDER, 85);
            expected.put(Animal.Type.CAT, 10500);
            Map<Animal.Type, Integer> actual = AnimalUtils.getWeightAnimalsWhoseHeightBetweenKAndI(animals, 5, 30);

            assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
        }

        @Test
        void shouldReturnEmptyMapForHeightBetween15And10() {
            Map<Animal.Type, Integer> actual = AnimalUtils.getWeightAnimalsWhoseHeightBetweenKAndI(animals, 15, 10);

            assertThat(actual).isEmpty();
        }

        @Test
        void shouldReturnEmptyMapForEmptyList() {
            Map<Animal.Type, Integer> actual =
                AnimalUtils.getWeightAnimalsWhoseHeightBetweenKAndI(new ArrayList<>(), 5, 30);

            assertThat(actual).isEmpty();
        }

        @Test
        void shouldReturnEmptyMapForNullList() {
            Map<Animal.Type, Integer> actual = AnimalUtils.getWeightAnimalsWhoseHeightBetweenKAndI(null, 5, 30);

            assertThat(actual).isEmpty();
        }
    }

    @Nested
    @DisplayName("Задача 16")
    class TestTask16 {
        @Test
        void shouldReturnListAnimalsSortedByTypeThenBySexThenByName() {
            List<Animal> expected = getCopyAnimals();
            expected.sort(Comparator.comparing(Animal::type).thenComparing(Animal::sex).thenComparing(Animal::name));
            List<Animal> actual = AnimalUtils.getAnimalsSortedByTypeThenBySexThenByName(animals);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            List<Animal> actual = AnimalUtils.getAnimalsSortedByTypeThenBySexThenByName(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyListForNullList() {
            List<Animal> actual = AnimalUtils.getAnimalsSortedByTypeThenBySexThenByName(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 17")
    class TestTask17 {
        @Test
        void shouldReturnIsSpidersBiteMoreOftenThanDogs() {
            Boolean actual = AnimalUtils.isSpidersBiteMoreOftenThanDogs(animals);

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnEmptyListForEmptyList() {
            Boolean actual = AnimalUtils.isSpidersBiteMoreOftenThanDogs(new ArrayList<>());

            assertThat(actual).isFalse();
        }

        @Test
        void shouldReturnEmptyListForNullList() {
            Boolean actual = AnimalUtils.isSpidersBiteMoreOftenThanDogs(null);

            assertThat(actual).isFalse();
        }
    }

    @Nested
    @DisplayName("Задача 18")
    class TestTask18 {

        @Test
        void shouldReturnHeaviestFishFromList() {
            Animal expected = animals.get(INDEX_OF_HEAVIEST_FISH);
            Optional<Animal> actual = AnimalUtils.getHeaviestFishFromLists(animals);

            assertThat(actual.isEmpty()).isFalse();
            assertThat(actual.get()).extracting(
                Animal::name,
                Animal::type,
                Animal::sex,
                Animal::age,
                Animal::height,
                Animal::weight,
                Animal::bites
            ).containsExactly(
                expected.name(),
                expected.type(),
                expected.sex(),
                expected.age(),
                expected.height(),
                expected.weight(),
                expected.bites()
            );
        }

        @Test
        void shouldReturnHeaviestFishFromLists() {
            Animal expected = new Animal("Tiny", Animal.Type.FISH, Animal.Sex.F, 1, 1, 50000, true);
            Optional<Animal> actual = AnimalUtils.getHeaviestFishFromLists(animals, List.of(expected));

            assertThat(actual.isEmpty()).isFalse();
            assertThat(actual.get()).extracting(
                Animal::name,
                Animal::type,
                Animal::sex,
                Animal::age,
                Animal::height,
                Animal::weight,
                Animal::bites
            ).containsExactly(
                expected.name(),
                expected.type(),
                expected.sex(),
                expected.age(),
                expected.height(),
                expected.weight(),
                expected.bites()
            );
        }

        @Test
        void shouldReturnEmptyOptionalForEmptyList() {
            Optional<Animal> actual = AnimalUtils.getHeaviestFishFromLists(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyOptionalForNullList() {
            Optional<Animal> actual = AnimalUtils.getHeaviestFishFromLists(null);

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 19")
    class TestTask19 {

        @Test
        void shouldReturnMapWithValidationErrors() {
            Map<String, Set<ValidationError>> expected = new HashMap<>();
            Set<ValidationError> set = new HashSet<>();
            set.add(ValidationError.WRONG_NAME);
            expected.put("Salvador Domingo Felipe Jacinto Dali i Domenech, Marquess of Dali of Pubol", set);
            Map<String, Set<ValidationError>> actual = AnimalUtils.getAnimalsWithErrorsInRecord(animals);

            assertThat(actual).containsExactlyInAnyOrderEntriesOf(expected);
        }

        @Test
        void shouldReturnMapWithAllValidationErrors() {
            List<Animal> incorrectAnimals = List.of(
                new Animal(null, null, Animal.Sex.F, 1, 1, 1, true),
                new Animal("", Animal.Type.CAT, null, 1, 1, 1, true),
                new Animal("1", Animal.Type.DOG, Animal.Sex.M, 0, -12, -45, true)
            );
            String[] expectedNames = new String[] {null, "", "1"};
            List<Set<ValidationError>> expectedErrors = new ArrayList<>();
            Set<ValidationError> set = new HashSet<>();
            set.add(ValidationError.NAME_IS_NULL);
            set.add(ValidationError.TYPE_IS_NULL);
            expectedErrors.add(set);

            set = new HashSet<>();
            set.add(ValidationError.EMPTY_NAME);
            set.add(ValidationError.SEX_IS_NULL);
            expectedErrors.add(set);

            set = new HashSet<>();
            set.add(ValidationError.WRONG_NAME);
            set.add(ValidationError.WRONG_AGE);
            set.add(ValidationError.WRONG_HEIGHT);
            set.add(ValidationError.WRONG_WEIGHT);
            expectedErrors.add(set);

            Map<String, Set<ValidationError>> actual = AnimalUtils.getAnimalsWithErrorsInRecord(incorrectAnimals);

            assertThat(actual).containsOnlyKeys(null, "", "1");
            for (int i = 0; i < expectedNames.length; i++) {
                assertThat(actual.get(expectedNames[i])).containsExactlyInAnyOrderElementsOf(expectedErrors.get(i));
            }

        }

        @Test
        void shouldReturnEmptyMapForNullList() {
            Map<String, Set<ValidationError>> actual = AnimalUtils.getAnimalsWithErrorsInRecord(null);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyMapForEmptyList() {
            Map<String, Set<ValidationError>> actual = AnimalUtils.getAnimalsWithErrorsInRecord(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("Задача 20")
    class TestTask20 {

        @Test
        void shouldReturnMapWithValidationErrors() {
            String expectedName = "Salvador Domingo Felipe Jacinto Dali i Domenech, Marquess of Dali of Pubol";
            Map<String, String> actual = AnimalUtils.getReadableAnimalsWithErrorsRecord(animals);

            assertThat(actual).containsOnlyKeys(expectedName);
            assertThat(actual.get(expectedName)).contains(ValidationError.WRONG_NAME.toString());
        }

        @Test
        void shouldReturnMapWithAllValidationErrors() {
            List<Animal> incorrectAnimals = List.of(
                new Animal(null, null, Animal.Sex.F, 1, 1, 1, true),
                new Animal("", Animal.Type.CAT, null, 1, 1, 1, true),
                new Animal("1", Animal.Type.DOG, Animal.Sex.M, 0, -12, -45, true)
            );
            String[] expectedNames = new String[] {null, "", "1"};
            String[][] expectedErrors = new String[][] {
                {"NAME_IS_NULL", "TYPE_IS_NULL"},
                {"EMPTY_NAME", "EMPTY_NAME"},
                {"WRONG_NAME", "WRONG_AGE", "WRONG_HEIGHT", "WRONG_WEIGHT"}
            };

            Map<String, String> actual = AnimalUtils.getReadableAnimalsWithErrorsRecord(incorrectAnimals);
            assertThat(actual).containsOnlyKeys(null, "", "1");
            for (int i = 0; i < expectedNames.length; i++) {
                for (int j = 0; j < expectedErrors[i].length; j++) {
                    assertThat(actual.get(expectedNames[i])).contains(expectedErrors[i][j]);
                }
            }

        }

        @Test
        void shouldReturnEmptyMapForNullList() {
            Map<String, String> actual = AnimalUtils.getReadableAnimalsWithErrorsRecord(null);

            assertThat(actual.isEmpty()).isTrue();
        }

        @Test
        void shouldReturnEmptyMapForEmptyList() {
            Map<String, String> actual = AnimalUtils.getReadableAnimalsWithErrorsRecord(new ArrayList<>());

            assertThat(actual.isEmpty()).isTrue();
        }
    }

    private static List<Animal> getCopyAnimals() {

        return new ArrayList<>(animals);
    }

}
