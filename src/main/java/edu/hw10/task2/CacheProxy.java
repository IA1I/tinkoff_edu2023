package edu.hw10.task2;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CacheProxy {
    private static final Logger LOGGER = LogManager.getLogger();
    private static Map<String, Object> cache = new HashMap<>();

    private CacheProxy() {
    }

    public static <T> T create(T object, Class<? extends T> objectsClass, Path file) {
        ClassLoader classLoader = objectsClass.getClassLoader();
        Class<?>[] interfaces = objectsClass.getInterfaces();
        InvocationHandler invocationHandler = (proxy, method, args) -> {
            String key = method.getName() + Arrays.toString(args);
            Object result;
            if (cache.containsKey(key)) {
                result = cache.get(key);
                LOGGER.info("Get method result from cache");
            } else {
                result = method.invoke(object, args);
                cache.put(key, result);
                LOGGER.info("Add method result from cache");
            }
            if (method.isAnnotationPresent(Cache.class)) {
                writeToFile(file, method, key, result);
            }

            return result;
        };

        return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }

    private static void writeToFile(Path file, Method method, String key, Object result) throws IOException {
        if (method.getAnnotation(Cache.class).persist()) {
            if (!Files.exists(file)) {
                Files.createFile(file);
                LOGGER.info("Create file {}", file);
            }
            String line = key + " : " + result;
            if (!isResultSaved(file, line)) {
                writeToFile(file, line);
            }
        }
    }

    private static void writeToFile(Path file, String line) {
        try (FileWriter fileWriter = new FileWriter(file.toFile(), true)) {
            fileWriter.write(line + '\n');
            LOGGER.info("Write method result to file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isResultSaved(Path file, String line) {
        try {
            List<String> allLines = Files.readAllLines(file);
            for (String s : allLines) {
                if (s.equals(line)) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
