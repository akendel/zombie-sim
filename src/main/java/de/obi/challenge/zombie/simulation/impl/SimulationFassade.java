package de.obi.challenge.zombie.simulation.impl;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.model.api.builder.SurvivorBuilder;
import de.obi.challenge.zombie.model.api.builder.ZombieBuilder;
import de.obi.challenge.zombie.simulation.api.Simulation;
import de.obi.challenge.zombie.simulation.api.SimulationConfig;
import de.obi.challenge.zombie.simulation.api.SimulationEventListener;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public class SimulationFassade implements Simulation {
    private static final Logger LOG = LoggerFactory.getLogger(SimulationFassade.class);
    private final MessageChannel pendingActorsChannel;
    private final ApplicationContext applicationContext;
    private SimulationEventListener simulationEventListener;
    private int zombieCounter = 0;
    private int survivorCounter = 0;
    private final StopWatch stopWatch = new StopWatch();

    SimulationFassade(
            @Qualifier("pendingActorsChannel") MessageChannel pendingActorsChannel,
            ApplicationContext applicationContext) {

        this.pendingActorsChannel = pendingActorsChannel;
        this.applicationContext = applicationContext;
    }

    @Override
    public void startSimulation(SimulationConfig simulationConfig) {
        stopWatch.start();
        LOG.info("Start simulation with {} survivors and {} zombies!",
                simulationConfig.numberOfSurvivors(),
                simulationConfig.numberOfZombies()
        );

        this.zombieCounter = simulationConfig.numberOfZombies();
        this.survivorCounter = simulationConfig.numberOfSurvivors();

        List<Zombie> zombies = IntStream.range(0, simulationConfig.numberOfZombies())
                .mapToObj(value -> getDefaultZombie(simulationConfig.accuracyOfZombies())).toList();

        List<Survivor> survivors = IntStream.range(0, simulationConfig.numberOfSurvivors())
                .mapToObj(value -> getDefaultSurvivors(simulationConfig.accuracyOfSurvivors())).toList();

        List<Actor> actors = new ArrayList<>(survivors);
        actors.addAll(zombies);
        Collections.shuffle(actors);

        actors.forEach(actor -> pendingActorsChannel.send(new GenericMessage<>(actor)));
    }

    private Survivor getDefaultSurvivors(int accuracy) {
        SurvivorBuilder survivorBuilder = applicationContext.getBean(SurvivorBuilder.class);
        return survivorBuilder.withDefaultAttack(accuracy)
                .withDefaultDefence()
                .build();
    }

    private Zombie getDefaultZombie(int accuracy) {
        ZombieBuilder zombieBuilder = applicationContext.getBean(ZombieBuilder.class);
        return zombieBuilder.withDefaultAttack(accuracy)
                .withDefaultDefence()
                .build();
    }

    @Override
    public void addEventListener(SimulationEventListener simulationEventListener) {
        this.simulationEventListener = simulationEventListener;
    }

    public synchronized void handleSimulationEvent(Message<SimulationEvent> message) {
        LOG.debug("Receive message {}", message.getPayload());
        switch (message.getPayload()) {
            case ZOMBIE_KILLED -> {
                simulationEventListener.zombieKilled();
                decreaseZombieCount();
            }
            case ZOMBIE_SURVIVED -> simulationEventListener.zombieSurvived();
            case SURVIVOR_KILLED -> {
                simulationEventListener.survivorKilled();
                decreaseSurvivorCount();
            }
            case NEW_ZOMBIE -> increaseZombieCount();
            case SURVIVOR_SURVIVED -> simulationEventListener.survivorSurvived();
            case PENDING_ZOMBIE -> simulationEventListener.zombieIsPending();
        }
    }

    private void increaseZombieCount() {
        this.zombieCounter = zombieCounter + 1;
        logCurrentState();
    }

    private void decreaseSurvivorCount() {
        this.survivorCounter = survivorCounter - 1;
        logCurrentState();
        if (this.survivorCounter == 0) {
            simulationFinished();
        }
    }

    private void decreaseZombieCount() {
        this.zombieCounter = zombieCounter - 1;
        logCurrentState();
        if (this.zombieCounter == 0) {
            simulationFinished();
        }
    }

    private void simulationFinished() {
        simulationEventListener.simulationFinished(
                zombieCounter,
                survivorCounter,
                Duration.ofSeconds(stopWatch.getTime(TimeUnit.SECONDS))
        );
    }

    private void logCurrentState() {
        LOG.debug("Simulation is running for {} seconds. {} survivors and {} zombies are alive",
                stopWatch.getTime(TimeUnit.SECONDS),
                survivorCounter,
                zombieCounter
        );
    }
}
