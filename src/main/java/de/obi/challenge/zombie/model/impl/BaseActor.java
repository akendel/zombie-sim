package de.obi.challenge.zombie.model.impl;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;

import java.util.UUID;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 Andreas Kendel
 */
public abstract class BaseActor implements Actor {
    private int health;
    private final AttackStrategy attackStrategy;
    private final DefenceStrategy defenceStrategy;
    private final UUID uuid = UUID.randomUUID();

    protected BaseActor(int health, AttackStrategy attackStrategy, DefenceStrategy defenceStrategy) {
        this.health = health;
        this.attackStrategy = attackStrategy;
        this.defenceStrategy = defenceStrategy;
    }
    @Override
    public UUID getId() {
        return uuid;
    }

    @Override
    public void attackedBy(Actor attacker) {
        int damage = getDefenseStrategy().calculateDamage(attacker.getAttackStrategy());
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
