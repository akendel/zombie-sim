package de.obi.challenge.zombie.simulation.api;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public record SimulationConfig(
        int numberOfZombies,
        int numberOfSurvivors,
        int accuracyOfZombies,
        int accuracyOfSurvivors) {
}
