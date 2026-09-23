package ch.realflorianchrist.caddms.directory;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DirectoryRepository extends Neo4jRepository<DirectoryEntity, UUID> {

}
