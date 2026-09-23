package ch.realflorianchrist.caddms.document;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface DocumentRepository extends Neo4jRepository<DocumentEntity, UUID> {

}
