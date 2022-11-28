package de.obi.challenge.zombie.simulation.impl.combat.command;

import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface Command {
    void execute(CombatContext combatContext);
}
