package dev.vality.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import dev.vality.geck.common.reflection.ClassFinder;
import dev.vality.geck.migrator.*;
import dev.vality.geck.migrator.kit.BaseMigrationSpec;
import dev.vality.geck.migrator.kit.SimpleMigrationPointProvider;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class JoltMigrationPointProvider extends SimpleMigrationPointProvider {

    public static String DEFAULT_REGEX = ".*_spec\\.json";

    public static String DEFAULT_PATH = "/jolt/";

    public JoltMigrationPointProvider(Collection<JoltSpec> specs) {
        super(createMigrationPoints(specs));
    }

    public static JoltMigrationPointProvider fromClasspath() throws MigrationException {
        return fromClasspath(DEFAULT_PATH, DEFAULT_REGEX);
    }

    public static JoltMigrationPointProvider fromClasspath(String path, String regex) throws MigrationException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<JoltSpec> joltSpecs = new ArrayList<>();
        try {
            for (String file : ClassFinder.findResources(path, regex)) {
                Map joltSpecMap = JsonUtils.jsonToMap(classLoader.getResourceAsStream(file));
                joltSpecs.add(createJoltSpec(joltSpecMap));
            }
            return new JoltMigrationPointProvider(joltSpecs);
        } catch (Exception e) {
            throw new MigrationException("Cannot create JoltSpec list", e);
        }
    }

    public static JoltMigrationPointProvider fromFileSystem(Path file) throws MigrationException {
        return fromFileSystem(file, DEFAULT_REGEX);
    }

    public static JoltMigrationPointProvider fromFileSystem(Path file, String regex) throws MigrationException {
        try {
            List<JoltSpec> joltSpecs = Files.walk(file)
                    .filter(path -> !Files.isDirectory(path) && path.toString().matches(regex))
                    .map(path -> JsonUtils.filepathToMap(path.toAbsolutePath().toString()))
                    .map(map -> createJoltSpec(map))
                    .collect(Collectors.toList());

            return new JoltMigrationPointProvider(joltSpecs);
        } catch (Exception e) {
            throw new MigrationException("Cannot create JoltSpec list", e);
        }
    }

    private static JoltSpec createJoltSpec(Map joltSpecMap) {
        if (joltSpecMap.containsKey("thriftSpec")
                && joltSpecMap.containsKey("joltSpec")) {
            ThriftSpec thriftSpec = createThriftSpec((Map) joltSpecMap.get("thriftSpec"));
            Chainr chainr = Chainr.fromSpec(joltSpecMap.get("joltSpec"));
            return new JoltSpec(thriftSpec, chainr);
        }
        throw new IllegalArgumentException("Incorrect jolt spec format");
    }

    private static ThriftSpec createThriftSpec(Map thriftSpecMap) {
        if (thriftSpecMap.containsKey("inDef")
                && thriftSpecMap.containsKey("outDef")) {
            return new ThriftSpec(
                    createThriftDef((Map) thriftSpecMap.get("inDef")),
                    createThriftDef((Map) thriftSpecMap.get("outDef"))
            );
        }
        throw new IllegalArgumentException("Incorrect thrift spec format");
    }

    private static ThriftDef createThriftDef(Map thriftDefMap) {
        if (thriftDefMap.containsKey("version")
                && thriftDefMap.containsKey("type")) {
            return new ThriftDef(
                    (int) thriftDefMap.get("version"),
                    (String) thriftDefMap.get("type")
            );
        }
        throw new IllegalArgumentException("Incorrect thrift def format");
    }

    private static List<MigrationPoint> createMigrationPoints(Collection<JoltSpec> specs) {
        AtomicInteger counter = new AtomicInteger(0);
        return specs.stream()
                .map(spec -> new MigrationPoint(
                        counter.incrementAndGet(),
                        spec.getThriftSpec(),
                        JoltMigrator.SERIALIZER_DEF,
                        new BaseMigrationSpec(spec.getChainr(), MigrationType.JOLT.getKey())
                )).collect(Collectors.toList());
    }

}
