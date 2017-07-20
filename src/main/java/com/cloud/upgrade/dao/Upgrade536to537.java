package com.cloud.upgrade.dao;

import com.cloud.exceptions.GeneralException;
import com.cloud.utils.Script;

import java.io.File;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Upgrade536to537 implements DbUpgrade {

    final static Logger logger = LoggerFactory.getLogger(Upgrade536to537.class);
    private static final String PREVIOUS_VERSION = "5.3.6";
    private static final String NEXT_VERSION = "5.3.7";
    private static final String SCHEMA_SCRIPT = "db/schema-536to537.sql";
    private static final String SCHEMA_CLEANUP_SCRIPT = "db/schema-536to537-cleanup.sql";

    @Override
    public String[] getUpgradableVersionRange() {
        return new String[]{PREVIOUS_VERSION, NEXT_VERSION};
    }

    @Override
    public String getUpgradedVersion() {
        return NEXT_VERSION;
    }

    @Override
    public boolean supportsRollingUpgrade() {
        return false;
    }

    @Override
    public File[] getPrepareScripts() {
        return getScript(SCHEMA_SCRIPT);
    }

    @Override
    public void performDataMigration(final Connection conn) {
        logger.info("Performing data migration from 5.3.6 to 5.3.7");
    }

    @Override
    public File[] getCleanupScripts() {
        return getScript(SCHEMA_CLEANUP_SCRIPT);
    }

    private File[] getScript(final String scriptName) {
        final String script = Script.findScript("", scriptName);
        if (script == null) {
            throw new GeneralException("Unable to find " + scriptName);
        }

        return new File[]{new File(script)};
    }
}
