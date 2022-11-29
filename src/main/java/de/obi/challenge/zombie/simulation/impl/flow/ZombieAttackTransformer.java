package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.impl.SimulationEvent;
import de.obi.challenge.zombie.simulation.impl.combat.ActorFactory;
import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public class ZombieAttackTransformer {
    private static final Logger LOG = LoggerFactory.getLogger(ZombieAttackTransformer.class);

    private final MessageChannel simulationEventChannel;

    private final ActorFactory actorFactory;

    public ZombieAttackTransformer(@Qualifier("simulationEventChannel") MessageChannel simulationEventChannel,
                                   @Autowired ActorFactory actorFactory) {
        this.simulationEventChannel = simulationEventChannel;
        this.actorFactory = actorFactory;
    }

    public CombatContext execute(CombatContext combatContext) {
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

            Zombie zombie = actorFactory.getDefaultZombie();
            LOG.debug("Zombie {} will be added to combat context", zombie.getId());
            combatContext.addZombie(zombie);
            simulationEventChannel.send(new GenericMessage<>(SimulationEvent.NEW_ZOMBIE));
        }
        return combatContext;
    }
}
