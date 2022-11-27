package de.obi.challenge.zombie.model.impl.builder;


import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.model.api.builder.ZombieBuilder;
import de.obi.challenge.zombie.model.impl.DefaultZombie;
import de.obi.challenge.zombie.model.impl.attack.AttackStrategy;
import de.obi.challenge.zombie.model.impl.attack.DefaultAttack;
import de.obi.challenge.zombie.model.impl.defence.DefaultDefence;
import de.obi.challenge.zombie.model.impl.defence.DefenceStrategy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
@Component
@Scope("prototype")
public class DefaultZombieBuilder implements ZombieBuilder {
    private DefenceStrategy defenceStrategy;
    private AttackStrategy attackStrategy;

    @Override
    public ZombieBuilder withDefaultAttack(int accuracy) {
        this.attackStrategy = new DefaultAttack(accuracy);
        return this;
    }

    @Override
    public ZombieBuilder withDefaultDefence() {
        this.defenceStrategy = new DefaultDefence();
        return this;
    }

    @Override
    public Zombie build() {
        return new DefaultZombie(1, attackStrategy, defenceStrategy);
    }
}
