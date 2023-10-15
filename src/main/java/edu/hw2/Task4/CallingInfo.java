package edu.hw2.Task4;

public record CallingInfo(String className, String methodName) {
    private static final String NO_ONE_CALLED = "Никто не вызывал";
    private static final int LENGTH_TO_CALLED_METHOD = 3;

    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (stackTraceElements.length >= LENGTH_TO_CALLED_METHOD) {
            StackTraceElement element = stackTraceElements[2];
            return new CallingInfo(element.getClassName(), element.getMethodName());
        }

        return new CallingInfo(NO_ONE_CALLED, "метода нет");
    }

    public static CallingInfo callingInfo(String methodName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (var element : stackTraceElements) {
            if (element.getMethodName().equals(methodName)) {
                return new CallingInfo(element.getClassName(), element.getMethodName());
            }
        }

        return new CallingInfo(NO_ONE_CALLED, methodName);
    }
}
