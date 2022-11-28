package de.obi.challenge.zombie.simulation.impl.combat.command;

import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
@Component
public class ReleasePendingActorsCommand implements Command{

    private static final Logger LOG = LoggerFactory.getLogger(ReleasePendingActorsCommand.class);

    private final MessageChannel pendingActorsChannel;

    public ReleasePendingActorsCommand(@Qualifier("pendingActorsChannel") MessageChannel pendingActorsChannel) {
        this.pendingActorsChannel = pendingActorsChannel;
    }

    @Override
    public void execute(CombatContext combatContext) {
        combatContext.getSurvivor().ifPresent(survivor -> {
            LOG.debug("Survivor {} has survived and will participate to the next combat round", survivor.getId());
            pendingActorsChannel.send(new GenericMessage<>(survivor));
        });
        combatContext.getZombies().forEach(zombie -> {
            LOG.debug("Zombie {} has survived and will participate to the next combat round", zombie.getId());
            pendingActorsChannel.send(new GenericMessage<>(zombie));
        });
    }
}
