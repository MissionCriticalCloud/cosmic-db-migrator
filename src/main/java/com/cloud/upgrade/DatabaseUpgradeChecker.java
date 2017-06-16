package com.cloud.upgrade;

import com.cloud.exceptions.GeneralException;
import com.cloud.upgrade.dao.DbUpgrade;
import com.cloud.upgrade.dao.Upgrade444to450;
import com.cloud.upgrade.dao.Upgrade450to451;
import com.cloud.upgrade.dao.Upgrade451to452;
import com.cloud.upgrade.dao.Upgrade452to460;
import com.cloud.upgrade.dao.Upgrade453to460;
import com.cloud.upgrade.dao.Upgrade460to461;
import com.cloud.upgrade.dao.Upgrade461to470;
import com.cloud.upgrade.dao.Upgrade470to471;
import com.cloud.upgrade.dao.Upgrade471to480;
import com.cloud.upgrade.dao.Upgrade480to500;
import com.cloud.upgrade.dao.Upgrade500to501;
import com.cloud.upgrade.dao.Upgrade501to510;
import com.cloud.upgrade.dao.Upgrade510to511;
import com.cloud.upgrade.dao.Upgrade511to520;
import com.cloud.upgrade.dao.Upgrade520to530;
import com.cloud.upgrade.dao.Upgrade530to531;
import com.cloud.upgrade.dao.Upgrade531to532;
import com.cloud.upgrade.dao.Upgrade532to533;
import com.cloud.upgrade.dao.Upgrade533to534;
import com.cloud.upgrade.dao.Upgrade534to535;
import com.cloud.upgrade.dao.Upgrade535to536;
import com.cloud.upgrade.repository.Version;
import com.cloud.upgrade.repository.Version.Step;
import com.cloud.upgrade.repository.VersionRepository;
import com.cloud.utils.GlobalLock;
import com.cloud.utils.ScriptRunner;
import com.cloud.utils.TransactionLegacy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUpgradeChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseUpgradeChecker.class);

    private final HashMap <String, DbUpgrade[]> upgradeMap = new HashMap <>();

    private final VersionRepository versionRepository;

    public DatabaseUpgradeChecker(final VersionRepository versionRepository) {
        this.versionRepository = versionRepository;

        upgradeMap.put("4.4.4", new DbUpgrade[]{new Upgrade444to450(), new Upgrade450to451(), new Upgrade451to452(), new Upgrade452to460(), new Upgrade460to461(), new
                Upgrade461to470(), new Upgrade470to471(), new Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new
                Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new
                Upgrade535to536()});

        upgradeMap.put("4.5.0", new DbUpgrade[]{new Upgrade450to451(), new Upgrade451to452(), new Upgrade452to460(), new Upgrade460to461(), new Upgrade461to470(), new
                Upgrade470to471(), new Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new
                Upgrade520to530(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.5.1", new DbUpgrade[]{new Upgrade451to452(), new Upgrade452to460(), new Upgrade460to461(), new Upgrade461to470(), new Upgrade470to471(), new
                Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new
                Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.5.2", new DbUpgrade[]{new Upgrade452to460(), new Upgrade460to461(), new Upgrade461to470(), new Upgrade470to471(), new Upgrade471to480(), new
                Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new
                Upgrade531to532(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.5.3", new DbUpgrade[]{new Upgrade453to460(), new Upgrade460to461(), new Upgrade461to470(), new Upgrade470to471(), new Upgrade471to480(), new
                Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new
                Upgrade531to532(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.6.0", new DbUpgrade[]{new Upgrade460to461(), new Upgrade461to470(), new Upgrade470to471(), new Upgrade471to480(), new Upgrade480to500(), new
                Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new
                Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.6.1", new DbUpgrade[]{new Upgrade461to470(), new Upgrade470to471(), new Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new
                Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new
                Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.6.2", new DbUpgrade[]{new Upgrade461to470(), new Upgrade470to471(), new Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new
                Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new
                Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.7.0", new DbUpgrade[]{new Upgrade470to471(), new Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new
                Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new
                Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("4.7.1", new DbUpgrade[]{new Upgrade471to480(), new Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new
                Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new
                Upgrade535to536()});

        upgradeMap.put("4.8.0", new DbUpgrade[]{new Upgrade480to500(), new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new
                Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.0.0", new DbUpgrade[]{new Upgrade500to501(), new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new
                Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.0.1", new DbUpgrade[]{new Upgrade501to510(), new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new
                Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.1.0", new DbUpgrade[]{new Upgrade510to511(), new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new
                Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.1.1", new DbUpgrade[]{new Upgrade511to520(), new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new
                Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.2.0", new DbUpgrade[]{new Upgrade520to530(), new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new
                Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.3.0", new DbUpgrade[]{new Upgrade530to531(), new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new
                Upgrade535to536()});

        upgradeMap.put("5.3.1", new DbUpgrade[]{new Upgrade531to532(), new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.3.2", new DbUpgrade[]{new Upgrade532to533(), new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.3.3", new DbUpgrade[]{new Upgrade533to534(), new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.3.4", new DbUpgrade[]{new Upgrade534to535(), new Upgrade535to536()});

        upgradeMap.put("5.3.5", new DbUpgrade[]{new Upgrade535to536()});
    }

    public void check() {
        final GlobalLock lock = GlobalLock.getInternLock("DatabaseUpgrade");
        try {
            LOGGER.info("Grabbing lock to check for database upgrade.");
            if (!lock.lock(20 * 60)) {
                throw new GeneralException("Unable to acquire lock to check for database integrity.");
            }

            try {
                final Version version = versionRepository.findFirst1ByOrderByIdDesc();

                if (version == null) {
                    throw new GeneralException("No version found!");
                }
                final String dbVersion = version.getVersion();

                // We're always upgrading to the latest version!
                final String currentVersion = "5.3.6";

                LOGGER.info("DB version = " + dbVersion + " Code Version = " + currentVersion);

                if (com.cloud.utils.Version.compare(com.cloud.utils.Version.trimToPatch(dbVersion), com.cloud.utils.Version.trimToPatch(currentVersion)) > 0) {
                    throw new GeneralException("Database version " + dbVersion + " is higher than management software version " + currentVersion);
                }

                if (com.cloud.utils.Version.compare(com.cloud.utils.Version.trimToPatch(dbVersion), com.cloud.utils.Version.trimToPatch(currentVersion)) == 0) {
                    LOGGER.info("DB version and code version matches so no upgrade needed.");
                    return;
                }

                upgrade(dbVersion, currentVersion);
            } finally {
                lock.unlock();
            }
        } finally {
            lock.releaseRef();
        }
    }

    protected void upgrade(final String dbVersion, final String currentVersion) {
        LOGGER.info("Database upgrade must be performed from " + dbVersion + " to " + currentVersion);

        final String trimmedDbVersion = com.cloud.utils.Version.trimToPatch(dbVersion);
        final String trimmedCurrentVersion = com.cloud.utils.Version.trimToPatch(currentVersion);

        final DbUpgrade[] upgrades = upgradeMap.get(trimmedDbVersion);
        if (upgrades == null) {
            LOGGER.error("There is no upgrade path from " + dbVersion + " to " + currentVersion);
            throw new GeneralException("There is no upgrade path from " + dbVersion + " to " + currentVersion);
        }

        for (final DbUpgrade upgrade : upgrades) {
            Version version;
            if (com.cloud.utils.Version.compare(upgrade.getUpgradedVersion(), trimmedCurrentVersion) > 0) {
                break;
            }
            LOGGER.debug("Running upgrade " + upgrade.getClass().getSimpleName() + " to upgrade from " + upgrade.getUpgradableVersionRange()[0] + "-" +
                    upgrade.getUpgradableVersionRange()[1] + " to " + upgrade.getUpgradedVersion());
            TransactionLegacy txn = TransactionLegacy.open("Upgrade");
            txn.start();
            try {
                final Connection conn;
                try {
                    conn = txn.getConnection();
                } catch (final SQLException e) {
                    final String errorMessage = "Unable to upgrade the database";
                    LOGGER.error(errorMessage, e);
                    throw new GeneralException(errorMessage, e);
                }
                final File[] scripts = upgrade.getPrepareScripts();
                if (scripts != null) {
                    for (final File script : scripts) {
                        runScript(conn, script);
                    }
                }

                upgrade.performDataMigration(conn);
                version = new Version(upgrade.getUpgradedVersion());
                version = versionRepository.save(version);

                txn.commit();
            } catch (final GeneralException e) {
                final String errorMessage = "Unable to upgrade the database";
                LOGGER.error(errorMessage, e);
                throw new GeneralException(errorMessage, e);
            } finally {
                txn.close();
            }

            // Run the corresponding '-cleanup.sql' script
            txn = TransactionLegacy.open("Cleanup");
            try {
                LOGGER.info("Cleanup upgrade " + upgrade.getClass().getSimpleName() + " to upgrade from " + upgrade.getUpgradableVersionRange()[0] + "-" +
                        upgrade.getUpgradableVersionRange()[1] + " to " + upgrade.getUpgradedVersion());

                txn.start();

                final Connection conn;
                try {
                    conn = txn.getConnection();
                } catch (final SQLException e) {
                    LOGGER.error("Unable to cleanup the database", e);
                    throw new GeneralException("Unable to cleanup the database", e);
                }

                final File[] scripts = upgrade.getCleanupScripts();
                if (scripts != null) {
                    for (final File script : scripts) {
                        runScript(conn, script);
                        LOGGER.debug("Cleanup script " + script.getAbsolutePath() + " is executed successfully");
                    }
                }
                txn.commit();

                txn.start();
                version.setStep(Step.Complete);
                version.setUpdated(new Date());
                versionRepository.save(version);
                txn.commit();
                LOGGER.debug("Upgrade completed for version " + version.getVersion());
            } finally {
                txn.close();
            }
        }
    }

    protected void runScript(final Connection conn, final File file) {

        try (FileReader reader = new FileReader(file)) {
            LOGGER.info("Running DB script: " + file.getName());
            final ScriptRunner runner = new ScriptRunner(conn, false, true);
            runner.runScript(reader);
        } catch (final FileNotFoundException e) {
            LOGGER.error("Unable to find upgrade script: " + file.getAbsolutePath(), e);
            throw new GeneralException("Unable to find upgrade script: " + file.getAbsolutePath(), e);
        } catch (final IOException e) {
            LOGGER.error("Unable to read upgrade script: " + file.getAbsolutePath(), e);
            throw new GeneralException("Unable to read upgrade script: " + file.getAbsolutePath(), e);
        } catch (final SQLException e) {
            LOGGER.error("Unable to execute upgrade script: " + file.getAbsolutePath(), e);
            throw new GeneralException("Unable to execute upgrade script: " + file.getAbsolutePath(), e);
        }
    }
}
