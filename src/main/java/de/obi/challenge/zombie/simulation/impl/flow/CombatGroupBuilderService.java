package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public class CombatGroupBuilderService {
    private static final Logger LOG = LoggerFactory.getLogger(CombatGroupBuilderService.class);

    private final MessageChannel combatGroupsChannel;

    private List<Zombie> zombieBuffer = new ArrayList<>();

    public CombatGroupBuilderService(@Qualifier("combatGroupsChannel") MessageChannel combatGroupsChannel) {
        this.combatGroupsChannel = combatGroupsChannel;
    }

    public void handleActor(Message<Actor> message) {
        Actor actor = message.getPayload();
        LOG.debug("Handle actor of type {} with id {}", actor.getClass().getSimpleName(), actor.getId());

        if(actor instanceof Survivor survivor) {
            LOG.debug("Create new combat group with actor {} and {} zombies", actor.getId(), zombieBuffer.size());
            CombatContext combatContext = new CombatContext(survivor, zombieBuffer);
            zombieBuffer.clear();
            combatGroupsChannel.send(new GenericMessage<>(combatContext));
        } else {
            LOG.debug("Buffer zombie {} until the next survivor arrives", actor.getId());
            zombieBuffer.add((Zombie)actor);
        }
    }
}
