package ch.realflorianchrist.caddms.metadata;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface MetadataDefinitionRepository extends Neo4jRepository<MetadataDefinitionEntity, UUID> {

}
