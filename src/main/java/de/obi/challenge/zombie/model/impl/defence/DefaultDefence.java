package de.obi.challenge.zombie.model.impl.defence;

import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public class DefaultDefence implements DefenceStrategy {
    @Override
    public int calculateDamage(AttackStrategy attackStrategy) {
        if(attackStrategy.hasHit()) {
            return attackStrategy.getDamage();
        }
        return 0;
    }
}
