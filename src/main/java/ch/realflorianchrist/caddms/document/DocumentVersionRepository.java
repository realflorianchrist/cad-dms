package ch.realflorianchrist.caddms.document;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DocumentVersionRepository extends Neo4jRepository<DocumentVersionEntity, UUID> {

}
