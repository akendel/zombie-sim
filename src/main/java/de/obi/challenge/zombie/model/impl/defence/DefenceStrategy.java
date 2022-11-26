package de.obi.challenge.zombie.model.impl.defence;

import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface DefenceStrategy {
    int calculateDamage(AttackStrategy attackStrategy);
}
