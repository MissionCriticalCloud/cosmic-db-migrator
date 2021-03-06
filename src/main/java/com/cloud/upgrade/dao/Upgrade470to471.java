package com.cloud.upgrade.dao;

import com.cloud.exceptions.GeneralException;
import com.cloud.utils.Script;

import java.io.File;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Upgrade470to471 implements DbUpgrade {
    final static Logger s_logger = LoggerFactory.getLogger(Upgrade470to471.class);

    @Override
    public String[] getUpgradableVersionRange() {
        return new String[]{"4.7.0", "4.7.1"};
    }

    @Override
    public String getUpgradedVersion() {
        return "4.7.1";
    }

    @Override
    public boolean supportsRollingUpgrade() {
        return false;
    }

    @Override
    public File[] getPrepareScripts() {
        final String script = Script.findScript("", "db/schema-470to471.sql");
        if (script == null) {
            throw new GeneralException("Unable to find db/schema-470to471.sql");
        }
        return new File[]{new File(script)};
    }

    @Override
    public void performDataMigration(final Connection conn) {
    }

    @Override
    public File[] getCleanupScripts() {
        final String script = Script.findScript("", "db/schema-470to471-cleanup.sql");
        if (script == null) {
            throw new GeneralException("Unable to find db/schema-470to471-cleanup.sql");
        }

        return new File[]{new File(script)};
    }
}
