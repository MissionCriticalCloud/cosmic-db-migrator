package com.cloud.upgrade.dao;

import com.cloud.exceptions.GeneralException;
import com.cloud.utils.Script;

import java.io.File;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Upgrade480to500 implements DbUpgrade {
    final static Logger s_logger = LoggerFactory.getLogger(Upgrade480to500.class);

    @Override
    public String[] getUpgradableVersionRange() {
        return new String[]{"4.8.0", "5.0.0"};
    }

    @Override
    public String getUpgradedVersion() {
        return "5.0.0";
    }

    @Override
    public boolean supportsRollingUpgrade() {
        return false;
    }

    @Override
    public File[] getPrepareScripts() {
        final String script = Script.findScript("", "db/schema-480to500.sql");
        if (script == null) {
            throw new GeneralException("Unable to find db/schema-480to500.sql");
        }
        return new File[]{new File(script)};
    }

    @Override
    public void performDataMigration(final Connection conn) {
    }

    @Override
    public File[] getCleanupScripts() {
        final String script = Script.findScript("", "db/schema-480to500-cleanup.sql");
        if (script == null) {
            throw new GeneralException("Unable to find db/schema-480to500-cleanup.sql");
        }

        return new File[]{new File(script)};
    }
}
