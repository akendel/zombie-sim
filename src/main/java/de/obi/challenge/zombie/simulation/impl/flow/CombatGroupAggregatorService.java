package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.impl.SimulationEvent;
import de.obi.challenge.zombie.simulation.impl.combat.CombatGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.LinkedList;
import java.util.Queue;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public class CombatGroupAggregatorService {
    private static final Logger LOG = LoggerFactory.getLogger(CombatGroupAggregatorService.class);

    private final MessageChannel combatGroupsChannel;
    private final MessageChannel simulationEventChannel;

    private final Queue<Zombie> zombieBuffer = new LinkedList<>();

    private final Queue<Survivor> survivorBuffer = new LinkedList<>();

    public CombatGroupAggregatorService(
            @Qualifier("combatGroupsChannel") MessageChannel combatGroupsChannel,
            @Qualifier("simulationEventChannel") MessageChannel simulationEventChannel
    ) {
        this.combatGroupsChannel = combatGroupsChannel;
        this.simulationEventChannel = simulationEventChannel;
    }

    public void handleActor(Message<Actor> message) {
        Actor actor = message.getPayload();
        LOG.debug("Handle actor of type {} with id {}", actor.getClass().getSimpleName(), actor.getId());

        synchronized (this) {
            if(actor instanceof Survivor survivor) {
                LOG.debug("Add survivor {} to buffer of size {} ", actor.getId(), survivorBuffer.size());
                survivorBuffer.add(survivor);
            } else if (actor instanceof Zombie zombie){
                LOG.debug("Add zombie {} to buffer of size {} ", actor.getId(), zombieBuffer.size());
                simulationEventChannel.send(new GenericMessage<>(SimulationEvent.PENDING_ZOMBIE));
                zombieBuffer.add(zombie);
            } else {
                throw new IllegalStateException("Unsupported payload " + message.getPayload());
            }

            if (!zombieBuffer.isEmpty() && !survivorBuffer.isEmpty()) {
                LOG.debug("Create new combat group with actor {} and {} zombies", actor.getId(), zombieBuffer.size());
                CombatGroup combatGroup = new CombatGroup(survivorBuffer.poll(), zombieBuffer.stream().toList());
                zombieBuffer.clear();
                combatGroupsChannel.send(new GenericMessage<>(combatGroup));
            }
        }
    }
}
