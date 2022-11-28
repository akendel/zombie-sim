package de.obi.challenge.zombie.simulation.impl.combat;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.simulation.impl.combat.chain.CommandChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public class CombatService {
    private static final Logger LOG = LoggerFactory.getLogger(CombatService.class);
    private final CommandChain commandChain;

    @Autowired
    public CombatService(CommandChain commandChain) {
        this.commandChain = commandChain;
    }

    public void fight(CombatContext combatContext) {
        Survivor survivor = combatContext.getSurvivor().orElseThrow();
        LOG.debug("Start combat with survivor {} and {} zombies", survivor.getId(), combatContext.getZombies().size());
        commandChain.execute(combatContext);
    }
}
