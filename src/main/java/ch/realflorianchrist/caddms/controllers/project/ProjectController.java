package ch.realflorianchrist.caddms.controllers.project;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import ch.realflorianchrist.caddms.project.ProjectEntity;
import ch.realflorianchrist.caddms.project.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProjectController {

    private final ProjectRepository projectRepository;

    @QueryMapping
    public ProjectEntity project(@Argument("projectId") UUID projectId) {
        return projectRepository.findById(projectId)
                .orElse(null);
    }

    @QueryMapping
    public List<ProjectEntity> projects() {
        return projectRepository.findAll();
    }
}
