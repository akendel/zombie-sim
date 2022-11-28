package de.obi.challenge.zombie.userinterface;

import de.obi.challenge.zombie.simulation.api.Simulation;
import de.obi.challenge.zombie.simulation.api.SimulationEventListener;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */

@Component
public class ConsoleOut {
    private final Simulation simulation;

    @Autowired
    public ConsoleOut(Simulation simulation) {
        this.simulation = simulation;
    }

    @PostConstruct
    public void addEventListener() {
        simulation.addEventListener(new SimulationEventListener() {
            @Override
            public void zombieKilled() {
                printToFrontend("Klatsch!");
            }

            @Override
            public void zombieSurvived() {
                printToFrontend("Mist!");
            }

            @Override
            public void survivorKilled() {
                // Nothing is printed to the console when a survivor dies.
            }

            @Override
            public void survivorSurvived() {
                printToFrontend("Juhu!");
            }

            @Override
            public void simulationFinished(int numberOfZombies, int numberOfSurvivors, Duration duration) {
                String durationMessage = String.format("Simulation was running for %s minutes and %s seconds", duration.toMinutesPart(), duration.toSecondsPart());
                printToFrontend(durationMessage);

                String resultMessage = numberOfZombies > numberOfSurvivors ? numberOfZombies + " zombies" : numberOfSurvivors + " survivors";
                printToFrontend(String.format("%s have won the battle", resultMessage));
            }

            @Override
            public void zombieIsPending() {
                printToFrontend("Grrrr");
            }
        });
    }

    private void printToFrontend(String message) {
        System.out.println(message);
        System.out.flush();
    }
}
