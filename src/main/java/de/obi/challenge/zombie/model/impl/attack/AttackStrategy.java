package de.obi.challenge.zombie.model.impl.attack;

/**
 * This interface is used to implement the different attack strategies that an
 * {@link de.obi.challenge.zombie.model.api.Actor} could use.
 *
 * @author 26.11.22 Andreas Kendel
 */
public interface AttackStrategy {
    int getDamage();

    boolean hasHit();
}
