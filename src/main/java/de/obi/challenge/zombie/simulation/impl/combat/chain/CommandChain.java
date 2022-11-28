package de.obi.challenge.zombie.simulation.impl.combat.chain;

import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface CommandChain {
    void execute(CombatContext combatContext);
}
