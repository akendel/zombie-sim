package de.obi.challenge.zombie.model.api.builder;

import de.obi.challenge.zombie.model.api.Survivor;

/**
 * Builder for creating survivor instances
 *
 * @author 26.11.22 Andreas Kendel
 */
public interface SurvivorBuilder {
    SurvivorBuilder withDefaultAttack(int accuracy);
    SurvivorBuilder withDefaultDefence();
    Survivor build();
}
