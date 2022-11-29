package de.obi.challenge.zombie.simulation.impl;

/**
 * Represents the internal events that can be fired by the simulation.
 *
 * @author 27.11.22 Andreas Kendel
 */
public enum SimulationEvent {
    ZOMBIE_KILLED,
    ZOMBIE_SURVIVED,
    SURVIVOR_KILLED,
    SURVIVOR_SURVIVED,
    NEW_ZOMBIE,
    PENDING_ZOMBIE,
}
