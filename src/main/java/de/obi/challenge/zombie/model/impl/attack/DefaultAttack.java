package de.obi.challenge.zombie.model.impl.attack;

import org.apache.commons.lang3.RandomUtils;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public class DefaultAttack implements AttackStrategy {
    private final int accuracy;

    public DefaultAttack(int accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public int getDamage() {
        return 1;
    }

    public boolean hasHit() {
        return RandomUtils.nextInt(0, 100) < accuracy;
    }
}
