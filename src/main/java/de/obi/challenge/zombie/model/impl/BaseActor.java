package de.obi.challenge.zombie.model.impl;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public class BaseActor implements Actor {
    private int health;
    private final AttackStrategy attackStrategy;
    private final DefenceStrategy defenceStrategy;

    public BaseActor(int health, AttackStrategy attackStrategy, DefenceStrategy defenceStrategy) {
        this.health = health;
        this.attackStrategy = attackStrategy;
        this.defenceStrategy = defenceStrategy;
    }

    @Override
    public void attack(Actor victim) {
        int damage = victim.getDefenseStrategy().calculateDamage(attackStrategy);
        health = health - damage;
    }

    @Override
    public boolean isAlive() {
        return health > 0;
    }

    @Override
    public AttackStrategy getAttackStrategy() {
        return attackStrategy;
    }

    @Override
    public DefenceStrategy getDefenseStrategy() {
        return defenceStrategy;
    }
}
