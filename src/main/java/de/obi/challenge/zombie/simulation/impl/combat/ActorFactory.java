package de.obi.challenge.zombie.simulation.impl.combat;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.model.api.builder.SurvivorBuilder;
import de.obi.challenge.zombie.model.api.builder.ZombieBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;

/**
 * TODO: Insert Class Description...
 *
 * @author 28.11.22 %USER, empulse GmbH
 */
@Component
public class ActorFactory {
    private final ApplicationContext applicationContext;

    private int defaultSurvivorAccuracy;

    private int defaultZombieAccuracy;
    @Autowired
    public ActorFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Collection<Survivor> getDefaultSurvivors(int count) {
        return IntStream.range(0, count).mapToObj(value -> getDefaultSurvivors()).toList();
    }

    public Collection<Zombie> getDefaultZombies(int count) {
        return IntStream.range(0, count).mapToObj(value -> getDefaultZombie()).toList();
    }

    public Survivor getDefaultSurvivors() {
        SurvivorBuilder survivorBuilder = applicationContext.getBean(SurvivorBuilder.class);
        return survivorBuilder.withDefaultAttack(defaultSurvivorAccuracy)
                .withDefaultDefence()
                .build();
    }

    public Zombie getDefaultZombie() {
        ZombieBuilder zombieBuilder = applicationContext.getBean(ZombieBuilder.class);
        return zombieBuilder.withDefaultAttack(defaultZombieAccuracy)
                .withDefaultDefence()
                .build();
    }

    public void setDefaultSurvivorAccuracy(int defaultSurvivorAccuracy) {
        this.defaultSurvivorAccuracy = defaultSurvivorAccuracy;
    }

    public void setDefaultZombieAccuracy(int defaultZombieAccuracy) {
        this.defaultZombieAccuracy = defaultZombieAccuracy;
    }
}
