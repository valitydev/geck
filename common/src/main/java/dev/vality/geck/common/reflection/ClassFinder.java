package dev.vality.geck.common.reflection;

import org.reflections.Configuration;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClassFinder {

    public static <T> Collection<Class<? extends T>> find(Collection<String> scannedPackages,
                                                          String classNameSuffix,
                                                          Class<T> classType) {
        Set<Class<? extends T>> classes = new HashSet<>();
        for (String scannedPackage : scannedPackages) {
            classes.addAll(find(scannedPackage, classNameSuffix, classType));
        }
        return classes;
    }

    public static <T> Collection<Class<? extends T>> find(String scannedPackage,
                                                          String classSuffix,
                                                          Class<T> classType) {
        return new Reflections(scannedPackage)
                .getSubTypesOf(classType).stream()
                .filter(t -> t.getSimpleName().endsWith(classSuffix))
                .collect(Collectors.toSet());
    }

    public static Set<String> findResources(String path, String regex) {
        Configuration configuration = new ConfigurationBuilder()
                .setScanners(new ResourcesScanner())
                .forPackages(path);
        return new Reflections(configuration).getResources(Pattern.compile(regex));
    }

}
