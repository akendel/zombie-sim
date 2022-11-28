package de.obi.challenge.zombie.simulation.impl.combat.command;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.model.api.builder.ZombieBuilder;
import de.obi.challenge.zombie.simulation.impl.SimulationEvent;
import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
@Component
public class ZombieAttackCommand implements Command {
    private static final Logger LOG = LoggerFactory.getLogger(ZombieAttackCommand.class);

    private final MessageChannel simulationEventChannel;

    private final ApplicationContext applicationContext;

    public ZombieAttackCommand(@Qualifier("simulationEventChannel") MessageChannel simulationEventChannel,
                               @Autowired ApplicationContext applicationContext) {
        this.simulationEventChannel = simulationEventChannel;
        this.applicationContext = applicationContext;
    }

    @Override
    public void execute(CombatContext combatContext) {
        LOG.debug("Start zombie attack round with {} zombies", combatContext.getZombies().size());
        Survivor survivor = combatContext.getSurvivor().orElseThrow();
        combatContext.getZombies().forEach(zombie -> {
            LOG.debug("Zombie {} attacks survivor {}", zombie.getId(), survivor.getId());
            survivor.attackedBy(zombie);
        });

        if (survivor.isAlive()) {
            LOG.debug("Survivor {} has survived", survivor.getId());
            simulationEventChannel.send(new GenericMessage<>(SimulationEvent.SURVIVOR_SURVIVED));
        } else {
            LOG.debug("Survivor {} has died", survivor.getId());
            combatContext.removeSurvivor();
            simulationEventChannel.send(new GenericMessage<>(SimulationEvent.SURVIVOR_KILLED));

            //TODO Use global factory to create new zombies.
            ZombieBuilder zombieBuilder = applicationContext.getBean(ZombieBuilder.class);
            Zombie zombie = zombieBuilder.withDefaultDefence().withDefaultAttack(50).build();
            LOG.debug("Zombie {} will be added to combat context", zombie.getId());
            combatContext.addZombie(zombie);
        }
    }
}
