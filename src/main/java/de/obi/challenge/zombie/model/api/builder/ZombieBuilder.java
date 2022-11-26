package de.obi.challenge.zombie.model.api.builder;

import de.obi.challenge.zombie.model.api.Actor;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface ZombieBuilder {
    ZombieBuilder withDefaultAttack(int accuracy);
    ZombieBuilder withDefaultDefence();
    Actor build();
}
