package B;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.Objects;

public class BClass extends Agent {

    @Override
    protected void setup() {
        System.out.println("Agent B is ready too: " + getAID().getName() + "Let`s go");
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage receivedMessage = receive();
                if (receivedMessage != null) {
                    System.out.println(" - " + myAgent.getLocalName() + " received: " + receivedMessage.getContent());
                    ACLMessage reply = receivedMessage.createReply();
                    int perform = ACLMessage.NOT_UNDERSTOOD;
                    String msg = "I`m not understood";
                    if (Objects.equals(receivedMessage.getContent(), "Ping")) {
                        perform = ACLMessage.INFORM;
                        msg = "Pong";
                    }
                    reply.setPerformative(perform);
                    reply.setContent(msg);
                    send(reply);
                }
                block();
            }
        });
    }
}
