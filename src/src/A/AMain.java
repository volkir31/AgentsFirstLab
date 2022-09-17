package A;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Random;

public class AMain extends Agent {
    @Override
    protected void setup() {
        System.out.println("Hello from agent A: " + getAID().getName() + "Ready!");
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage receivedMessage = receive();

                if (receivedMessage != null) {
                    System.out.println(" - " + myAgent.getLocalName() + " received: " + receivedMessage.getContent());
                }

                block();
            }
        });

        AMSAgentDescription[] agents = null;

        try {
            agents = AMSService.search(this, new AMSAgentDescription());
        } catch (FIPAException e) {
            System.out.println("oops not found AMS: " + e);
            e.printStackTrace();
        }

        assert agents != null;

        for (AMSAgentDescription agent : agents) {
            AID agentId = agent.getName();
            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(agentId);
            message.setLanguage("English");
            int rand = new Random().nextInt(7 - 1) + 1;
            String messageContent = rand == 3 ? "Bong" : "Ping";
            message.setContent(messageContent);

            send(message);
        }
    }
}
