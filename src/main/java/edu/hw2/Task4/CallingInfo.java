package edu.hw2.Task4;

public record CallingInfo(String className, String methodName) {
    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (stackTraceElements.length >= 3) {
            StackTraceElement element = stackTraceElements[2];
            return new CallingInfo(element.getClassName(), element.getMethodName());
        }

        return new CallingInfo("Никто не вызывал", "метода нет");
    }

    public static CallingInfo callingInfo(String methodName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        for (var element : stackTraceElements) {
            if (element.getMethodName().equals(methodName)) {
                return new CallingInfo(element.getClassName(), element.getMethodName());
            }
        }

        return new CallingInfo("Никто не вызывал", methodName);
    }
}
