package de.obi.challenge.zombie.userinterface;

import de.obi.challenge.zombie.simulation.api.Simulation;
import de.obi.challenge.zombie.simulation.api.SimulationEventListener;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */

@Component
public class ConsoleOut {
    private final Simulation simulation;

    public ConsoleOut(@Autowired Simulation simulation) {
        this.simulation = simulation;
    }

    @PostConstruct
    public void addEventListener() {
        simulation.addEventListener(new SimulationEventListener() {
            @Override
            public void zombieKilled() {
                System.out.println("Klatch!");
            }

            @Override
            public void zombieSurvived() {
                System.out.println("Mist!");
            }

            @Override
            public void survivorKilled() {

            }

            @Override
            public void survivorSurvived() {
                System.out.println("Juhu!");
            }
        });
    }
}
