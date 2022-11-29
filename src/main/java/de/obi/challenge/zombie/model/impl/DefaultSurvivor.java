package de.obi.challenge.zombie.model.impl;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 Andreas Kendel
 */
public class DefaultSurvivor extends BaseActor implements Survivor {
    public DefaultSurvivor(int health, AttackStrategy attackStrategy, DefenceStrategy defenceStrategy) {
        super(health, attackStrategy, defenceStrategy);
    }
}
