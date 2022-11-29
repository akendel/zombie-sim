package de.obi.challenge.zombie.simulation.api;

import java.time.Duration;

/**
 * This listener can be used to listen to simulation events.
 *
 * @author 27.11.22 Andreas Kendel
 */
public interface SimulationEventListener {
    void zombieKilled();

    void zombieSurvived();

    void survivorKilled();

    void survivorSurvived();

    void simulationFinished(int numberOfZombies, int numberOfSurvivors, Duration duration);

    void zombieIsPending();
}
