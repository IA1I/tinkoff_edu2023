package edu.hw1;

public class Task1 {
    private static final String COLON = ":";
    private static final int NUMBER_OF_SECTIONS = 2;
    private static final int WRONG_ANSWER = -1;
    private static final int SIXTY_SECONDS = 60;

    private Task1() {
    }

    public static int minutesToSeconds(String videoLength) {
        int durationInSeconds;
        int[] duration;
        try {
            duration = inputProcessing(videoLength);
            durationInSeconds = duration[0] * SIXTY_SECONDS + duration[1];
        } catch (RuntimeException e) {
            durationInSeconds = WRONG_ANSWER;
        }

        return durationInSeconds;
    }

    private static int[] inputProcessing(String videoLength) {
        String[] input = videoLength.split(COLON);
        int[] duration = new int[NUMBER_OF_SECTIONS];
        if (input.length != NUMBER_OF_SECTIONS) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < NUMBER_OF_SECTIONS; i++) {
            duration[i] = Integer.parseInt(input[i]);
        }
        if (duration[1] >= SIXTY_SECONDS) {
            throw new IllegalArgumentException();
        }

        return duration;
    }
}
