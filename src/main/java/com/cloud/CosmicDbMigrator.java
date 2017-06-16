package com.cloud;

import com.cloud.upgrade.DatabaseUpgradeChecker;
import com.cloud.upgrade.repository.VersionRepository;
import com.cloud.utils.DbProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class CosmicDbMigrator implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(CosmicDbMigrator.class);

    private final VersionRepository versionRepository;

    @Autowired
    @Lazy
    public CosmicDbMigrator(final VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    @Override
    public void run(final ApplicationArguments args) {
        if (!(args.containsOption("cosmic.host") && args.containsOption("cosmic.port") && args.containsOption("cosmic.user") && args.containsOption("cosmic.password"))) {

            logger.info("--------------------------------------------------------------------");
            logger.info("");
            logger.info("Usage:");
            logger.info("    ./cosmic-db-migrator --cosmic.host=HOST --cosmic.port=PORT --cosmic.user=USER --cosmic.password=PASSWORD");
            logger.info("");
            logger.info("--------------------------------------------------------------------");

            return;
        }

        logger.info("");
        logger.info("Cosmic database migration tool started");
        logger.info("This will upgrade from any version between 4.4.4 and 5.3.6 to version 5.3.6");
        logger.info("");
        logger.info("-------------------------");
        logger.info("");
        logger.info("User input:");
        logger.info("");
        logger.info("host: " + args.getOptionValues("cosmic.host").get(0));
        logger.info("port: " + args.getOptionValues("cosmic.port").get(0));
        logger.info("user: " + args.getOptionValues("cosmic.user").get(0));
        logger.info("password: ***** (obfuscated)");
        logger.info("");
        logger.info("-------------------------");
        logger.info("");

        DbProperties.getDbProperties(
                args.getOptionValues("cosmic.host").get(0),
                args.getOptionValues("cosmic.port").get(0),
                args.getOptionValues("cosmic.user").get(0),
                args.getOptionValues("cosmic.password").get(0)
        );

        final DatabaseUpgradeChecker databaseUpgradeChecker = new DatabaseUpgradeChecker(versionRepository);

        databaseUpgradeChecker.check();

        logger.info("");
        logger.info("");
        logger.info("");
        logger.info("");
        logger.info("You have arrived at Cosmic version 5.3.6!");
        logger.info("");
        logger.info("Now go use Cosmic! :D");
        logger.info("");
        logger.info("");
        logger.info("");
        logger.info("");
    }

    public static void main(final String[] args) throws Exception {
        SpringApplication.run(CosmicDbMigrator.class, args);
    }
}