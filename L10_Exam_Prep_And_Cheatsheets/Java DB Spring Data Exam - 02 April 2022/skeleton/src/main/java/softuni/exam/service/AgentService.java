package softuni.exam.service;


import softuni.exam.models.entities.Agent;

import java.io.IOException;
import java.util.Optional;

// TODO: Implement all methods
public interface AgentService {

    boolean areImported();

    String readAgentsFromFile() throws IOException;
	
	String importAgents() throws IOException;
    Optional<Agent> findAgentByFirstName(String firstName);

}
