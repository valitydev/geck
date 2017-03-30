package com.rbkmoney.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationManager;
import com.rbkmoney.geck.migrator.MigrationManagerBuilder;
import com.rbkmoney.geck.migrator.ThriftDef;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * Created by tolkonepiu on 22/03/2017.
 */
public class JoltMigratorTest {

    @Test
    public void migration1Test() throws MigrationException {
        MigrationManager migrationManager = new MigrationManagerBuilder()
                .setMigrationPointProviders(Arrays.asList(JoltMigrationPointProvider.fromClasspath()))
                .setMigrators(Arrays.asList(new JoltMigrator()))
                .build();
        Map inputMap = JsonUtils.classpathToMap("/jolt/json_input.json");
        Map migrationMap = (Map) migrationManager.migrate(inputMap, new ThriftDef(0), JoltMigrator.SERIALIZER_DEF);

        InputStream outResource = this.getClass().getResourceAsStream("/jolt/json_output.json");
        assertEquals(new BufferedReader(new InputStreamReader(outResource))
                .lines().collect(Collectors.joining("\n")), JsonUtils.toPrettyJsonString(migrationMap));
    }
}
