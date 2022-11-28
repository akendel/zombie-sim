package de.obi.challenge.zombie.simulation.impl.combat.chain;

import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;
import de.obi.challenge.zombie.simulation.impl.combat.command.Command;
import de.obi.challenge.zombie.simulation.impl.combat.command.SurvivorAttackCommand;
import de.obi.challenge.zombie.simulation.impl.combat.command.ZombieAttackCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
@Component
public class DelayedCommandChain implements CommandChain {
    private static final Logger LOG = LoggerFactory.getLogger(DelayedCommandChain.class);
    private final List<Command> commandList;

    @Autowired
    public DelayedCommandChain(
            ZombieAttackCommand zombieAttackCommand,
            SurvivorAttackCommand survivorAttackCommand
    ) {
        this.commandList = Arrays.asList(
                survivorAttackCommand,
                zombieAttackCommand
        );
    }

    @Override
    public void execute(CombatContext combatContext) {
        commandList.forEach(command -> {
            try {
                LOG.debug("Execute command {}", command.getClass().getSimpleName());
                command.execute(combatContext);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        });
    }
}
