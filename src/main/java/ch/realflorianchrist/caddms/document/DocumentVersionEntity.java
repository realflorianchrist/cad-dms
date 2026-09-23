package ch.realflorianchrist.caddms.document;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("DocumentVersion")
public record DocumentVersionEntity(

        @Id UUID id,

        int number,

        String name,

        String extension,

        String contentHash,

        String storageKey,

        long fileSize,

        Instant createdAt,

        UUID createdBy

) {

}
