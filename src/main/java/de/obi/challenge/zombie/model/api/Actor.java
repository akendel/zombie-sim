package de.obi.challenge.zombie.model.api;

import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;

import java.util.UUID;

/**
 * The actor interface provides some basic methods for objects that can interact in a combat with each other.
 *
 * @author 26.11.22 Andreas Kendel
 */
public interface Actor {
    UUID getId();
    void attackedBy(Actor actor);
    boolean isAlive();
    AttackStrategy getAttackStrategy();
    DefenceStrategy getDefenseStrategy();
}
