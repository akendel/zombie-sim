package de.obi.challenge.zombie.simulation.impl.flow;

import de.obi.challenge.zombie.model.api.Survivor;
import de.obi.challenge.zombie.model.api.Zombie;
import de.obi.challenge.zombie.simulation.impl.SimulationEvent;
import de.obi.challenge.zombie.simulation.impl.combat.CombatGroup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.integration.test.matcher.MockitoMessageMatchers.messageWithPayload;

@ExtendWith(MockitoExtension.class)
class SurvivorAttackTransformerTest {

    @Mock
    private MessageChannel simulationEventChannel;

    @Mock
    private CombatGroup combatGroup;

    @InjectMocks
    private SurvivorAttackTransformer survivorAttackTransformer;

    @Test
    void testWithSingleZombieIsKilled() {
        Survivor survivor = mock(Survivor.class);
        Zombie zombie = mock(Zombie.class);
        when(zombie.isAlive()).thenReturn(false);

        when(combatGroup.getSurvivor()).thenReturn(Optional.of(survivor));
        when(combatGroup.getZombies()).thenReturn(Arrays.asList(zombie));
        survivorAttackTransformer.execute(combatGroup);

        verify(zombie).attackedBy(survivor);
        verify(combatGroup).removeZombie(zombie);

        verify(simulationEventChannel).send(messageWithPayload(SimulationEvent.ZOMBIE_KILLED));
        verify(simulationEventChannel, never()).send(messageWithPayload(SimulationEvent.ZOMBIE_SURVIVED));
    }

    @Test
    void testWithSingleZombieHasSurvives() {
        Survivor survivor = mock(Survivor.class);
        Zombie zombie = mock(Zombie.class);
        when(zombie.isAlive()).thenReturn(true);

        when(combatGroup.getSurvivor()).thenReturn(Optional.of(survivor));
        when(combatGroup.getZombies()).thenReturn(Arrays.asList(zombie));
        survivorAttackTransformer.execute(combatGroup);

        verify(zombie).attackedBy(survivor);
        verify(combatGroup, never()).removeZombie(zombie);

        verify(simulationEventChannel).send(messageWithPayload(SimulationEvent.ZOMBIE_SURVIVED));
        verify(simulationEventChannel, never()).send(messageWithPayload(SimulationEvent.ZOMBIE_KILLED));
    }


    @Test
    void testWithZwoZombiesFromGroupAreKilled() {
        Survivor survivor = mock(Survivor.class);
        Zombie zombie1 = mock(Zombie.class);
        Zombie zombie2 = mock(Zombie.class);
        Zombie zombie3 = mock(Zombie.class);
        Zombie zombie4 = mock(Zombie.class);
        Zombie zombie5 = mock(Zombie.class);
        when(zombie1.isAlive()).thenReturn(false);
        when(zombie2.isAlive()).thenReturn(true);
        when(zombie3.isAlive()).thenReturn(true);
        when(zombie4.isAlive()).thenReturn(false);
        when(zombie5.isAlive()).thenReturn(true);
        List<Zombie> zombies = Arrays.asList(
                zombie1,
                zombie2,
                zombie3,
                zombie4,
                zombie5
        );

        when(combatGroup.getSurvivor()).thenReturn(Optional.of(survivor));
        when(combatGroup.getZombies()).thenReturn(zombies);
        survivorAttackTransformer.execute(combatGroup);

        verify(zombie1).attackedBy(survivor);
        verify(zombie2).attackedBy(survivor);
        verify(zombie3).attackedBy(survivor);
        verify(zombie4).attackedBy(survivor);
        verify(zombie5).attackedBy(survivor);
        verify(combatGroup).removeZombie(zombie1);
        verify(combatGroup).removeZombie(zombie4);

        verify(simulationEventChannel, times(2))
                .send(messageWithPayload(SimulationEvent.ZOMBIE_KILLED));
        verify(simulationEventChannel, times(3))
                .send(messageWithPayload(SimulationEvent.ZOMBIE_SURVIVED));
    }
}