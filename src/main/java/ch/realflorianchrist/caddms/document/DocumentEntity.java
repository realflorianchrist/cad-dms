package ch.realflorianchrist.caddms.document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Document")
public record DocumentEntity(

        @Id UUID id,

        Instant createdAt,

        UUID createdBy,

        boolean archived,

        @Relationship(type = "CURRENT_VERSION") DocumentVersionEntity currentVersion,

        @Relationship(type = "HAS_VERSION") List<DocumentVersionEntity> versions

) {

    public DocumentEntity {
        versions = versions == null
                ? List.of()
                : List.copyOf(versions);

        if (currentVersion != null &&
                !versions.contains(currentVersion)) {
            throw new IllegalArgumentException(
                    "Current version must be part of versions");
        }
    }

    public static DocumentEntity create(
            UUID createdBy,
            DocumentVersionEntity initialVersion) {
        return new DocumentEntity(
                UUID.randomUUID(),
                Instant.now(),
                createdBy,
                false,
                initialVersion,
                List.of(initialVersion));
    }
}
