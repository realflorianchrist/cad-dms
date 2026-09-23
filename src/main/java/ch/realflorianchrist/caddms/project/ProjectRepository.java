package ch.realflorianchrist.caddms.project;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProjectRepository extends Neo4jRepository<ProjectEntity, UUID> {

}
