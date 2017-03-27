package com.rbkmoney.geck.common.reflection;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by vpankrashkin on 22.03.17.
 * Based on http://stackoverflow.com/a/15519745/3007501
 */


public class ClassFinder {
    private static final Logger log = LoggerFactory.getLogger(ClassFinder.class);

    private static final char PKG_SEPARATOR = '.';

    private static final char DIR_SEPARATOR = '/';

    private static final String CLASS_FILE_SUFFIX = ".class";

    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    public static <T> List<Class<T>> find(Collection<String> scannedPackages, String classNameSuffix, Class<T> classType) {
        List<Class<T>> classes = new ArrayList<>();
        for (String scannedPackage : scannedPackages) {
            String scannedPath = scannedPackage.replace(PKG_SEPARATOR, DIR_SEPARATOR);
            final String fullClassNameSuffix = classNameSuffix + CLASS_FILE_SUFFIX;
            URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
            if (scannedUrl == null) {
                log.warn(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
                continue;
            }

            try {
                System.out.println("ClassFinder:Scanned URL "+scannedUrl);
                File scannedDir = Paths.get(scannedUrl.toURI()).toFile();
                //File scannedDir = new File(scannedUrl.getFile());
                System.out.println("Absolute Path:"+scannedDir.getAbsolutePath());
                System.out.println("Exists:"+scannedDir.exists());
                System.out.println("Is directory:"+scannedDir.isDirectory());
                System.out.println("Can read:"+scannedDir.canRead());
                System.out.println("Can write:"+scannedDir.canWrite());
                System.out.println("Can execute:"+scannedDir.canExecute());
                File[] filesList = scannedDir.listFiles();
                if (filesList != null) {
                    for (File file : filesList) {
                        classes.addAll(find(file, scannedPackage, fullClassNameSuffix, classType));
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private static <T> List<Class<T>> find(File file, String scannedPackage, String fullClassFileSuffix, Class<T> classType) {

        List<Class<T>> classes = new ArrayList<>();
        String resource = scannedPackage + PKG_SEPARATOR + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource, fullClassFileSuffix, classType));
            }
        } else if (resource.endsWith(fullClassFileSuffix)) {
            int endIndex = resource.length() - CLASS_FILE_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                Class cl = Class.forName(className);
                if (classType.isAssignableFrom(cl)) {
                    classes.add(cl);
                }
            } catch (ClassNotFoundException ignore) {
            }
        }
        return classes;
    }

    public static <T> Collection<Class<T>> gFind(Collection<String> scannedPackages, String classNameSuffix, Class<T> classType) {
        List<Class<T>> classes = new ArrayList<>();
        for (String scannedPackage: scannedPackages) {
            classes.addAll(gFind(scannedPackage, classNameSuffix, classType));
        }
        return classes;
    }

        public static <T> Collection<Class<T>> gFind(String scannedPackage, String classSuffix, Class<T> classType) {
        Set<Class<T>> classes = new HashSet<>();
        try {
            ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            ImmutableSet<ClassPath.ClassInfo> classesInfo = classPath.getTopLevelClassesRecursive(scannedPackage);

            for (ClassPath.ClassInfo classInfo: classesInfo) {
                if (classInfo.getName().endsWith(classSuffix)) {
                    try {
                        Class cl = classInfo.load();
                        if (classType.isAssignableFrom(cl)) {
                            classes.add(cl);
                        }
                    } catch (Exception e) {
                        log.warn("Failed to load class "+ classInfo, e);
                    }
                }
            }
        } catch (IOException e) {
            log.warn("Failed to get classes list for package "+ scannedPackage, e);
        }
        return classes;
    }

}
