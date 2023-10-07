package edu.hw1;

public class Task1 {
    public static final String COLON = ":";
    public static final int ARRAY_SIZE = 2;
    public static final int WRONG_ANSWER = -1;
    public static final int SIXTY_SECONDS = 60;
    public static int minutesToSeconds(String videoLength){
        int durationInSeconds;
        int[] duration;
        try {
            duration = inputProcessing(videoLength);
            durationInSeconds = duration[0]*SIXTY_SECONDS + duration[1];
        } catch (Exception e){
            durationInSeconds = WRONG_ANSWER;
        }


        return durationInSeconds;
    }

    private static int[] inputProcessing(String videoLength) throws Exception {
        String[] input = videoLength.split(COLON);
        int[] duration = new int[ARRAY_SIZE];
        for(int i = 0; i < ARRAY_SIZE; i++){
            duration[i] = Integer.parseInt(input[i]);
        }
        if(duration[1] >= SIXTY_SECONDS){
            throw new Exception();
        }

        return duration;
    }
}
