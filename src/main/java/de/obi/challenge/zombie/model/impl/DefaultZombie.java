package de.obi.challenge.zombie.model.impl;

import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public class DefaultZombie extends BaseActor implements Zombie {
    public DefaultZombie(int health, AttackStrategy attackStrategy, DefenceStrategy defenceStrategy) {
        super(health, attackStrategy, defenceStrategy);
    }
}
