package ch.realflorianchrist.caddms.document;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import ch.realflorianchrist.caddms.user.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Node("DocumentVersion")
public class DocumentVersionEntity {

    @Id
    private UUID documentVersionId;

    @Version
    private Long persistenceVersion;

    private int number;

    private String name;

    private String extension;

    private String contentHash;

    private String storageKey;

    private long fileSize;

    private Instant createdAt;

    @Relationship(type = "CREATED_BY")
    private UserEntity createdBy;

    public DocumentVersionEntity(
            int number,
            String name,
            String extension,
            String contentHash,
            String storageKey,
            long fileSize,
            UserEntity createdBy) {

        this.documentVersionId = UUID.randomUUID();
        this.number = number;
        this.name = name;
        this.extension = extension;
        this.contentHash = contentHash;
        this.storageKey = storageKey;
        this.fileSize = fileSize;
        this.createdAt = Instant.now();
        this.createdBy = createdBy;
    }

}
