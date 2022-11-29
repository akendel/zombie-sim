package de.obi.challenge.zombie.simulation.api;

/**
 * This interface can be used to start the simulation and to observe the simulation events.
 *
 * @author 27.11.22 Andreas Kendel
 */
public interface Simulation {
    void startSimulation(SimulationConfig simulationConfig);

    void addEventListener(SimulationEventListener simulationEventListener);
}
