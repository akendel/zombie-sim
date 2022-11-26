package de.obi.challenge.zombie.model.api.builder;

import de.obi.challenge.zombie.model.api.Actor;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public interface SurvivorBuilder {
    SurvivorBuilder withDefaultAttack(int accuracy);
    SurvivorBuilder withDefaultDefence();
    Actor build();
}
