package ch.realflorianchrist.caddms.project;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import ch.realflorianchrist.caddms.directory.DirectoryEntity;
import ch.realflorianchrist.caddms.document.DocumentEntity;
import ch.realflorianchrist.caddms.user.UserEntity;
import ch.realflorianchrist.caddms.metadata.MetadataBinding;
import ch.realflorianchrist.caddms.metadata.MetadataValue;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Node("Project")
public class ProjectEntity {

    @Id
    private UUID projectId;

    @Version
    private Long persistenceVersion;

    private String name;

    private Instant createdAt;

    private boolean archived;

    @Relationship(type = "CREATED_BY")
    private UserEntity createdBy;

    @Relationship(type = "HAS_DOCUMENT")
    private List<DocumentEntity> documents = new ArrayList<>();

    @Relationship(type = "HAS_DIRECTORY")
    private List<DirectoryEntity> directories = new ArrayList<>();

    @Relationship(type = "HAS_METADATA")
    private List<MetadataBinding> metadataBindings = new ArrayList<>();

    @Relationship(type = "HAS_METADATA_VALUE")
    private List<MetadataValue> metadataValues = new ArrayList<>();

    public ProjectEntity(String name, UserEntity createdBy) {
        this.projectId = UUID.randomUUID();
        this.name = name;
        this.createdAt = Instant.now();
        this.archived = false;
        this.createdBy = createdBy;
    }
}
