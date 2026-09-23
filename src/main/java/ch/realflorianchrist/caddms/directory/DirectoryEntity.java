package ch.realflorianchrist.caddms.directory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import ch.realflorianchrist.caddms.document.DocumentEntity;
import ch.realflorianchrist.caddms.user.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Node("Directory")
public class DirectoryEntity {

    @Id
    private UUID directoryId;

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

    public DirectoryEntity(String name, UserEntity createdBy) {
        this.directoryId = UUID.randomUUID();
        this.name = name;
        this.createdAt = Instant.now();
        this.createdBy = createdBy;
        this.archived = false;
    }

}
