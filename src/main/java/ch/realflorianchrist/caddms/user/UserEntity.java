package ch.realflorianchrist.caddms.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import ch.realflorianchrist.caddms.project.ProjectAccessEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Node("User")
public class UserEntity {

    @Id
    private UUID userId;

    @Version
    private Long persistenceVersion;

    private String identityProvider;

    private String externalSubjectId;

    private String displayName;

    private boolean active;

    @Relationship(type = "HAS_ACCESS")
    private List<ProjectAccessEntity> projectAccess = new ArrayList<>();

    public UserEntity(String identityProvider, String externalSubjectId, String displayName, boolean active) {
        this.userId = UUID.randomUUID();
        this.identityProvider = identityProvider;
        this.externalSubjectId = externalSubjectId;
        this.displayName = displayName;
        this.active = active;
    }
}
