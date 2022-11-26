package de.obi.challenge.zombie.model.api;

import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface Actor {
    void attack(Actor actor);
    boolean isAlive();
    AttackStrategy getAttackStrategy();
    DefenceStrategy getDefenseStrategy();
}
