package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.simulation.impl.combat.CombatContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * TODO: Insert Class Description...
 *
 * @author 28.11.22 %USER, empulse GmbH
 */
public class CombatContextSplitter {
    public Collection<Actor> splitCombatContext(CombatContext combatContext) {
        List<Actor> result = new ArrayList<>(combatContext.getZombies());
        combatContext.getSurvivor().ifPresent(result::add);
        return result;
    }
}
