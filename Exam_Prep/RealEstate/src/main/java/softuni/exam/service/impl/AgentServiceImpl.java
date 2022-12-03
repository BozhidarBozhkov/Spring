package softuni.exam.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportAgentDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static softuni.exam.util.Constants.INVALID_AGENT;
import static softuni.exam.util.Constants.SUCCESSFULLY_IMPORTED_AGENT;
import static softuni.exam.util.Paths.AGENT_JSON_PATH;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final Gson gson;
    private final Validator validator;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;

        this.gson = new GsonBuilder().create();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
        this.modelMapper = new ModelMapper();
    }

    @Override
    public boolean areImported() {
        return this.agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        Path path = Path.of(AGENT_JSON_PATH);

        return Files.readString(path);
    }

    @Override
    public String importAgents() throws IOException {

        String json = this.readAgentsFromFile();

        ImportAgentDto[] importAgentDtos = this.gson.fromJson(json, ImportAgentDto[].class);


        return Arrays.stream(importAgentDtos).map(this::importAgent).collect(Collectors.joining("\n"));
    }

    private String importAgent(ImportAgentDto dto) {
        Set<ConstraintViolation<ImportAgentDto>> errors = this.validator.validate(dto);

        if (!errors.isEmpty()) {
            return INVALID_AGENT;
        }

        Optional<Agent> optAgent = this.agentRepository.findByFirstName(dto.getFirstName());

        if (optAgent.isPresent()) {
            return INVALID_AGENT;
        }
        Agent agent = this.modelMapper.map(dto, Agent.class);
        Optional<Town> optTown = this.townRepository.findByTownName(dto.getTownName());

        agent.setTown(optTown.get());

        this.agentRepository.save(agent);

        return String.format(SUCCESSFULLY_IMPORTED_AGENT, agent.getFirstName(), agent.getLastName());

    }
}
