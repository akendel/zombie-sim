package de.obi.challenge.zombie.userinterface.input;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 Andreas Kendel, empulse GmbH
 */
@Component
public class ConsoleInput implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleInput.class);

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Execute command line runner");

        Option numberOfZombies = new Option("z", "zombies", true, "Number of zombies");
        Option numberOfSurvivors = new Option("s", "survivors", true, "Number of survivors");

        Options options = new Options();
        options.addOption(numberOfSurvivors);
        options.addOption(numberOfZombies);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(numberOfSurvivors) && cmd.hasOption(numberOfZombies)) {
            startSimulation(
                    cmd.getOptionValue(numberOfSurvivors), 
                    cmd.getOptionValue(numberOfZombies)
            );
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("./gradlew bootRun --args='--zombies 100 --survivors 23", options);
        }
    }

    private void startSimulation(String numberOfSurvivors, String numberOfZombies) {
        LOG.info("Start simulation with {} survivors and {} zombies!", numberOfSurvivors, numberOfZombies);
    }
}
