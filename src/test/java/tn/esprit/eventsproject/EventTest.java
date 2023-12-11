package tn.esprit.eventsproject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.controllers.EventRestController;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.EventServicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventTest {

    @InjectMocks
    private EventRestController eventRestController;

    @Mock
    private EventServicesImpl eventServices;

    @Test
    public void testAddParticipant() {
        Participant participant = new Participant();
        participant.setIdParticipant(1);

        when(eventServices.addParticipant(participant)).thenReturn(participant);

        Participant result = eventRestController.addParticipant(participant);

        assertEquals(participant, result);
        verify(eventServices, times(1)).addParticipant(participant);
    }

    @Test
    public void testAddEventWithParticipant() {
        Event event = new Event();
        event.setIdEvent(1);

        int idPart = 1;

        when(eventServices.addAffectEvenParticipant(event, idPart)).thenReturn(event);

        Event result = eventRestController.addEventPart(event, idPart);

        assertEquals(event, result);
        verify(eventServices, times(1)).addAffectEvenParticipant(event, idPart);
    }

    @Test
    public void testAddEventWithoutParticipant() {
        Event event = new Event();
        event.setIdEvent(1);

        when(eventServices.addAffectEvenParticipant(event)).thenReturn(event);

        Event result = eventRestController.addEvent(event);

        assertEquals(event, result);
        verify(eventServices, times(1)).addAffectEvenParticipant(event);
    }

    @Test
    public void testAddAffectLog() {
        Logistics logistics = new Logistics();
        logistics.setIdLog(1);

        String descriptionEvent = "Test Event";

        when(eventServices.addAffectLog(logistics, descriptionEvent)).thenReturn(logistics);

        Logistics result = eventRestController.addAffectLog(logistics, descriptionEvent);

        assertEquals(logistics, result);
        verify(eventServices, times(1)).addAffectLog(logistics, descriptionEvent);
    }

    @Test
    public void testGetLogisticsDates() {
        LocalDate date_debut = LocalDate.of(2023, 1, 1);
        LocalDate date_fin = LocalDate.of(2023, 1, 5);

        List<Logistics> logisticsList = new ArrayList<>();
        logisticsList.add(new Logistics());

        when(eventServices.getLogisticsDates(date_debut, date_fin)).thenReturn(logisticsList);

        List<Logistics> result = eventRestController.getLogistiquesDates(date_debut, date_fin);

        assertEquals(logisticsList, result);
        verify(eventServices, times(1)).getLogisticsDates(date_debut, date_fin);
    }
}
