package de.obi.challenge.zombie.model.impl.defence;

import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;

/**
 * This interface is used to implement the different defence strategies that an
 * {@link de.obi.challenge.zombie.model.api.Actor} could use.
 *
 * @author 26.11.22 Andreas Kendel
 */
public interface DefenceStrategy {
    int calculateDamage(AttackStrategy attackStrategy);
}
