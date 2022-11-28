package de.obi.challenge.zombie.simulation.impl;

import de.obi.challenge.zombie.model.api.Actor;
import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.api.Simulation;
import de.obi.challenge.zombie.simulation.api.SimulationConfig;
import de.obi.challenge.zombie.simulation.api.SimulationEventListener;
import de.obi.challenge.zombie.simulation.impl.combat.ActorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * TODO: Insert Class Description...
 *
 * @author 27.11.22 %USER, empulse GmbH
 */
public class SimulationFassade implements Simulation {
    private static final Logger LOG = LoggerFactory.getLogger(SimulationFassade.class);
    private final MessageChannel pendingActorsChannel;
    private final ActorFactory actorFactory;
    private SimulationEventListener simulationEventListener;

    private SimulationState simulationState;

    SimulationFassade(
            @Qualifier("pendingActorsChannel") MessageChannel pendingActorsChannel,
            ActorFactory actorFactory) {

        this.pendingActorsChannel = pendingActorsChannel;
        this.actorFactory = actorFactory;
    }

    @Override
    public void startSimulation(SimulationConfig simulationConfig) {
        simulationState = new SimulationState(
                simulationConfig.numberOfZombies(),
                simulationConfig.numberOfSurvivors()
        );
        LOG.info("Start simulation with {} survivors and {} zombies!",
                simulationConfig.numberOfSurvivors(),
                simulationConfig.numberOfZombies()
        );

        actorFactory.setDefaultZombieAccuracy(simulationConfig.accuracyOfZombies());
        actorFactory.setDefaultSurvivorAccuracy(simulationConfig.accuracyOfSurvivors());
        Collection<Zombie> zombies = actorFactory.getDefaultZombies(simulationConfig.numberOfZombies());
        Collection<Survivor> survivors = actorFactory.getDefaultSurvivors(simulationConfig.numberOfSurvivors());

        List<Actor> actors = new ArrayList<>(survivors);
        actors.addAll(zombies);
        Collections.shuffle(actors);

        actors.forEach(actor -> pendingActorsChannel.send(new GenericMessage<>(actor)));
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
                simulationState.decreaseZombieCount();
                simulationState.logCurrentState();
                checkIfSimulationHasFinished();
            }
            case ZOMBIE_SURVIVED -> simulationEventListener.zombieSurvived();
            case SURVIVOR_KILLED -> {
                simulationEventListener.survivorKilled();
                simulationState.decreaseSurvivorCount();
                simulationState.logCurrentState();
                checkIfSimulationHasFinished();
            }
            case NEW_ZOMBIE -> simulationState.increaseZombieCount();
            case SURVIVOR_SURVIVED -> simulationEventListener.survivorSurvived();
            case PENDING_ZOMBIE -> simulationEventListener.zombieIsPending();
        }
    }



    private void checkIfSimulationHasFinished() {
        if(simulationState.isSimulationFinished()) {
            simulationEventListener.simulationFinished(
                    simulationState.getZombieCounter(),
                    simulationState.getSurvivorCounter(),
                    simulationState.getDuration()
            );
        }
    }


}
