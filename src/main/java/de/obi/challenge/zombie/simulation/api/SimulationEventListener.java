package de.obi.challenge.zombie.simulation.api;

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
}
