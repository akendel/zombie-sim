package de.obi.challenge.zombie.simulation.impl;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * This class hold the current internal state of the simulation.
 *
 * @author 28.11.22 Andreas Kendel
 */
public class SimulationState {
    private static final Logger LOG = LoggerFactory.getLogger(SimulationState.class);
    private final StopWatch stopWatch = new StopWatch();

    private int zombieCounter;

    private int survivorCounter;
    public SimulationState(int numberOfZombies, int numberOfSurvivors) {
        this.stopWatch.start();
        this.zombieCounter = numberOfZombies;
        this.survivorCounter = numberOfSurvivors;
    }

    public void increaseZombieCount() {
        this.zombieCounter = zombieCounter + 1;
        logCurrentState();
    }

    public void decreaseSurvivorCount() {
        this.survivorCounter = survivorCounter - 1;
    }

    public void decreaseZombieCount() {
        this.zombieCounter = zombieCounter - 1;
    }

    boolean isSimulationFinished() {
        return this.survivorCounter == 0 || this.zombieCounter == 0;
    }

    public void logCurrentState() {
        LOG.debug("Simulation is running for {} seconds. {} survivors and {} zombies are alive",
                stopWatch.getTime(TimeUnit.SECONDS),
                survivorCounter,
                zombieCounter
        );
    }

    public Duration getDuration() {
        return Duration.ofSeconds(stopWatch.getTime(TimeUnit.SECONDS));
    }

    public int getZombieCounter() {
        return zombieCounter;
    }

    public int getSurvivorCounter() {
        return survivorCounter;
    }
}
