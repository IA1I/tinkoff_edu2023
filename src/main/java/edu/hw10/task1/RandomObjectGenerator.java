package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomObjectGenerator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int BOUND_FOR_ARRAY_SIZE = 20;
    private static final String SUCCESSFUL_OBTAINING_PARAMETERS = "Got random parameters";
    private final Random random;

    public RandomObjectGenerator(Random random) {
        this.random = random;
    }

    public Object nextObject(Class<?> className) {
        Constructor<?> constructor = getConstructor(className);
        Object[] parametersForConstructor = getRandomParameters(constructor);
        return createInstance(constructor, parametersForConstructor);
    }

    public Object nextObject(Class<?> className, String methodName) {
        Method createMethod = getMethod(className, methodName);
        Object[] parametersForMethod = getRandomParameters(createMethod);
        Object result;
        try {
            result = createMethod.invoke(className, parametersForMethod);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        LOGGER.info("Created new instance {} with random fields by method {}", className, methodName);
        return result;
    }

    private Method getMethod(Class<?> className, String methodName) {
        Method[] methods = className.getMethods();
        Method createMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                createMethod = method;
            }
        }

        checkMethod(className, createMethod);
        LOGGER.info("Got creating method");
        return createMethod;
    }

    private void checkMethod(Class<?> className, Method createMethod) {
        if (createMethod == null) {
            LOGGER.error("No such method");
            throw new IllegalArgumentException();
        }
        if (!createMethod.getReturnType().equals(className)) {
            LOGGER.error("Wrong return type");
            throw new IllegalArgumentException();
        }
    }

    private Object createInstance(Constructor<?> constructor, Object[] parametersForConstructor) {
        Object object;
        try {
            object = constructor.newInstance(parametersForConstructor);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
        LOGGER.info("Created new instance {} with random fields", object.getClass());
        return object;
    }

    private Constructor<?> getConstructor(Class<?> className) {
        Constructor<?>[] constructors = className.getConstructors();
        Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));
        LOGGER.info("Got constructor {}", constructors[constructors.length - 1]);

        return constructors[constructors.length - 1];
    }

    private Object[] getRandomParameters(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
        Object[] randomParameters = new Object[parameterTypes.length];
        for (int i = 0; i < randomParameters.length; i++) {
            randomParameters[i] = getRandomObject(parameterTypes[i], parameterAnnotations[i]);
        }
        LOGGER.info(SUCCESSFUL_OBTAINING_PARAMETERS);
        return randomParameters;
    }

    private Object[] getRandomParameters(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] randomParameters = new Object[parameterTypes.length];
        for (int i = 0; i < randomParameters.length; i++) {
            randomParameters[i] = getRandomObject(parameterTypes[i], parameterAnnotations[i]);
        }
        LOGGER.info(SUCCESSFUL_OBTAINING_PARAMETERS);
        return randomParameters;
    }

    private Object getRandomObject(Class<?> fieldType, Annotation[] annotations) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        for (Annotation annotation : annotations) {
            String name = annotation.annotationType().getName();
            if (name.equals("edu.hw10.task1.annotations.Min")) {
                Min minAnnotation = (Min) annotation;
                min = minAnnotation.min();
            }
            if (name.equals("edu.hw10.task1.annotations.Max")) {
                Max maxAnnotation = (Max) annotation;
                max = maxAnnotation.max();
            }
        }

        if (min >= max) {
            LOGGER.error("Wrong annotations value: min >= max");
            throw new IllegalArgumentException();
        }
        return switch (fieldType.getName()) {
            case "int" -> random.nextInt(min, max);
            case "double" -> random.nextDouble(min, max);
            case "char" -> (char) random.nextInt(min, max);
            case "boolean" -> random.nextBoolean();
            case "[I" -> getRandomIntArray(min, max);
            default -> null;
        };
    }

    private Object getRandomIntArray(int min, int max) {
        int[] array = new int[random.nextInt(BOUND_FOR_ARRAY_SIZE)];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(min, max);
        }

        return array;
    }

}
