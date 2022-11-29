package de.obi.challenge.zombie.model.api.builder;

import de.obi.challenge.zombie.model.api.Zombie;

/**
 * Builder for creating zombie instances
 *
 * @author 26.11.22 Andreas Kendel
 */
public interface ZombieBuilder {
    ZombieBuilder withDefaultAttack(int accuracy);
    ZombieBuilder withDefaultDefence();
    Zombie build();
}
