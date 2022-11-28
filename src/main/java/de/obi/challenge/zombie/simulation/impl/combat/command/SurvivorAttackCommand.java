package de.obi.challenge.zombie.simulation.impl.combat.command;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.impl.SimulationEvent;
import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
@Component
public class SurvivorAttackCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(SurvivorAttackCommand.class);
    private final MessageChannel simulationEventChannel;

    public SurvivorAttackCommand(@Qualifier("simulationEventChannel") MessageChannel simulationEventChannel) {
        this.simulationEventChannel = simulationEventChannel;
    }

    @Override
    public void execute(CombatContext combatContext) {
        LOG.debug("Start survivor attack round with {} zombies", combatContext.getZombies().size());

        List<Zombie> zombies = combatContext.getZombies();
        Survivor survivor = combatContext.getSurvivor().orElseThrow();

        zombies.forEach(zombie -> {
            LOG.debug("Survivor {} attacks zombie {}", survivor.getId(), zombie.getId());
            survivor.attack(zombie);
        });

        List<Zombie> zombiesKilled = zombies.stream()
                .filter(zombie -> !zombie.isAlive()).toList();
        zombiesKilled.forEach(zombie -> {
            LOG.debug("Zombie {} has died", zombie.getId());
            simulationEventChannel.send(new GenericMessage<>(SimulationEvent.ZOMBIE_KILLED));
            combatContext.removeZombie(zombie);
        });

        zombies.stream()
                .filter(Actor::isAlive)
                .forEach(zombie -> {
                    LOG.debug("Zombie {} has survived", zombie.getId());
                    simulationEventChannel.send(new GenericMessage<>(SimulationEvent.ZOMBIE_SURVIVED));
                });

    }
}
