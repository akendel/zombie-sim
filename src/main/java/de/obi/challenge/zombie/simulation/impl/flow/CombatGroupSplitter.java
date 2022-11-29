package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.simulation.impl.combat.CombatGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This class splits a CombatGroup into a list of actors.
 *
 * @author 28.11.22 Andreas Kendel
 */
public class CombatGroupSplitter {
    public Collection<Actor> splitCombatGroup(CombatGroup combatGroup) {
        List<Actor> result = new ArrayList<>(combatGroup.getZombies());
        combatGroup.getSurvivor().ifPresent(result::add);
        return result;
    }
}
