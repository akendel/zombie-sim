package de.obi.challenge.zombie.simulation.api;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public interface Simulation {
    void startSimulation(SimulationConfig simulationConfig);

    void addEventListener(SimulationEventListener simulationEventListener);
}
