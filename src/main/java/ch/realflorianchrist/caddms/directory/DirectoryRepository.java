package ch.realflorianchrist.caddms.directory;

import java.util.List;
import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface DirectoryRepository extends Neo4jRepository<DirectoryEntity, UUID> {

    @Query("""
            MATCH (directory:Directory)
            WHERE NOT EXISTS {
                MATCH (:Directory)-[:HAS_DIRECTORY]->(directory)
            }
            OPTIONAL MATCH path = (directory)-[:HAS_DIRECTORY|HAS_DOCUMENT|CREATED_BY|HAS_METADATA|HAS_METADATA_VALUE|CURRENT_VERSION|HAS_VERSION*0..]->()
            RETURN directory, collect(nodes(path)) AS nodes, collect(relationships(path)) AS relationships
            """)
    List<DirectoryEntity> findRootDirectories();
}
