package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.impl.SimulationEvent;
import de.obi.challenge.zombie.simulation.impl.combat.ActorFactory;
import de.obi.challenge.zombie.simulation.impl.combat.CombatGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.integration.test.matcher.MockitoMessageMatchers.messageWithPayload;

@ExtendWith(MockitoExtension.class)
class ZombieAttackTransformerTest {

    @Mock
    private MessageChannel simulationEventChannel;

    @Mock
    private CombatGroup combatGroup;

    @Mock
    private ActorFactory actorFactory;

    @InjectMocks
    private ZombieAttackTransformer zombieAttackTransformer;

    @Test
    void testWithSurvivorIsKilled() {
        Zombie zombie = mock(Zombie.class);
        Survivor survivor = mock(Survivor.class);
        when(survivor.isAlive()).thenReturn(false);

        when(combatGroup.getSurvivor()).thenReturn(Optional.of(survivor));
        when(combatGroup.getZombies()).thenReturn(Arrays.asList(zombie));

        Zombie killedSurvivorAsZombie = mock(Zombie.class);
        when(actorFactory.getDefaultZombie()).thenReturn(killedSurvivorAsZombie);

        zombieAttackTransformer.execute(combatGroup);

        verify(survivor).attackedBy(zombie);
        verify(combatGroup).removeSurvivor();
        verify(combatGroup).addZombie(killedSurvivorAsZombie);

        verify(actorFactory).getDefaultZombie();
        verifyNoMoreInteractions(actorFactory);

        verify(simulationEventChannel).send(messageWithPayload(SimulationEvent.SURVIVOR_KILLED));
        verify(simulationEventChannel, never()).send(messageWithPayload(SimulationEvent.SURVIVOR_SURVIVED));
    }

    @Test
    void testWithSurvivorHasSurvived() {
        Survivor survivor = mock(Survivor.class);
        Zombie zombie = mock(Zombie.class);
        when(survivor.isAlive()).thenReturn(true);

        when(combatGroup.getSurvivor()).thenReturn(Optional.of(survivor));
        when(combatGroup.getZombies()).thenReturn(Arrays.asList(zombie));
        zombieAttackTransformer.execute(combatGroup);

        verify(survivor).attackedBy(zombie);
        verify(combatGroup, never()).removeSurvivor();
        verify(combatGroup, never()).addZombie(any(Zombie.class));
        verifyNoInteractions(actorFactory);

        verify(simulationEventChannel).send(messageWithPayload(SimulationEvent.SURVIVOR_SURVIVED));
        verify(simulationEventChannel, never()).send(messageWithPayload(SimulationEvent.SURVIVOR_KILLED));
    }

}