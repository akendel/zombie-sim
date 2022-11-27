package de.obi.challenge.zombie.model.api.builder;

import de.obi.challenge.zombie.model.api.Survivor;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface SurvivorBuilder {
    SurvivorBuilder withDefaultAttack(int accuracy);
    SurvivorBuilder withDefaultDefence();
    Survivor build();
}
