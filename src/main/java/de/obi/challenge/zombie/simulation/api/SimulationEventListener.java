package de.obi.challenge.zombie.simulation.api;

import java.time.Duration;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public interface SimulationEventListener {
    void zombieKilled();

    void zombieSurvived();

    void survivorKilled();

    void survivorSurvived();

    void simulationFinished(int numberOfZombies, int numberOfSurvivors, Duration duration);

    void zombieIsPending();
}
