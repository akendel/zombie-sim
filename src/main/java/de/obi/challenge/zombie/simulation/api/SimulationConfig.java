package de.obi.challenge.zombie.simulation.api;

/**
 * This class contains the parameters that should be used to start the simulation.
 *
 * @author 27.11.22 Andreas Kendel
 */
public record SimulationConfig(
        int numberOfZombies,
        int numberOfSurvivors,
        int accuracyOfZombies,
        int accuracyOfSurvivors) {
}
