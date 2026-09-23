package ch.realflorianchrist.caddms.document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
@Node("Document")
public class DocumentEntity {

    @Id
    private UUID documentId;

    @Version
    private Long persistenceVersion;

    private Instant createdAt;

    private boolean archived;

    @Relationship(type = "CREATED_BY")
    private UserEntity createdBy;

    @Relationship(type = "CURRENT_VERSION")
    private DocumentVersionEntity currentVersion;

    @Relationship(type = "HAS_VERSION")
    private List<DocumentVersionEntity> versions = new ArrayList<>();

    public DocumentEntity(UserEntity createdBy, DocumentVersionEntity currentVersion) {
        this.documentId = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.createdBy = createdBy;
        this.archived = false;
        this.currentVersion = currentVersion;
        this.versions.add(currentVersion);
    }

    public void addVersion(DocumentVersionEntity version) {
        this.versions.add(version);
        this.currentVersion = version;
    }
}
