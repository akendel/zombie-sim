package de.obi.challenge.zombie.simulation.impl.combat;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO: Insert Class Description...
 *
 * @author 26.11.22 %USER, empulse GmbH
 */
public class CombatContext {
    private final List<Zombie> zombies;

    private Survivor survivor;

    public CombatContext(Survivor survivor, List<Zombie> zombies) {
        this.survivor = survivor;
        this.zombies = new ArrayList<>(zombies);
    }

    public List<Zombie> getZombies() {
        return zombies;
    }

    public Optional<Survivor> getSurvivor() {
        return Optional.ofNullable(survivor);
    }

    public void removeZombie(Zombie zombie) {
        this.zombies.remove(zombie);
    }

    public void removeSurvivor() {
        this.survivor = null;
    }

    public void addZombie(Zombie zombie) {
        this.zombies.add(zombie);
    }
}
