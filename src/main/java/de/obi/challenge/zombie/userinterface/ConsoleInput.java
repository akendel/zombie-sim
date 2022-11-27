package de.obi.challenge.zombie.userinterface;

import de.obi.challenge.zombie.simulation.api.Simulation;
import de.obi.challenge.zombie.simulation.api.SimulationConfig;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Option NUMBER_OF_ZOMBIES = new Option("z", "zombies", true, "Number of zombies");
    private static final Option ZOMBIES_ACCURACY = new Option("za", "zombieAccuracy", true, "Accuracy zombie attacks");
    private static final Option NUMBER_OF_SURVIVORS = new Option("s", "survivors", true, "Number of survivors");
    private static final Option SURVIVOR_ACCURACY = new Option("sa", "survivorAccuracy", true, "Accuracy survivor attacks");
    private final Simulation simulation;

    public ConsoleInput(@Autowired Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Execute command line runner");

        Options options = new Options();
        options.addOption(NUMBER_OF_SURVIVORS);
        options.addOption(SURVIVOR_ACCURACY);
        options.addOption(NUMBER_OF_ZOMBIES);
        options.addOption(ZOMBIES_ACCURACY);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption(NUMBER_OF_SURVIVORS) && cmd.hasOption(NUMBER_OF_ZOMBIES)) {
            SimulationConfig config = buildConfiguration(cmd);
            simulation.startSimulation(config);
            LOG.info("Start simulation with {} survivors and {} zombies!", NUMBER_OF_SURVIVORS, NUMBER_OF_ZOMBIES);
        } else {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("./gradlew bootRun --args='--zombies 100 --survivors 23'", options);
        }
    }

    private SimulationConfig buildConfiguration(CommandLine cmd) {
        int numberOfZombies = Integer.parseInt(cmd.getOptionValue(NUMBER_OF_SURVIVORS));
        int numberOfSurvivors = Integer.parseInt(cmd.getOptionValue(NUMBER_OF_SURVIVORS));
        int accuracyOfZombieAttacks = Integer.parseInt(cmd.getOptionValue(ZOMBIES_ACCURACY));
        int accuracyOfSurvivorAttacks = Integer.parseInt(cmd.getOptionValue(SURVIVOR_ACCURACY));
        return new SimulationConfig(numberOfZombies, numberOfSurvivors, accuracyOfZombieAttacks, accuracyOfSurvivorAttacks);
    }
}
