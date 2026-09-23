package ch.realflorianchrist.caddms.project;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RelationshipProperties
public class ProjectAccessEntity {

    @Id
    @GeneratedValue
    private String id;

    @Version
    private Long persistenceVersion;

    private ProjectRole role;

    private Instant grantedAt;

    @TargetNode
    private ProjectEntity project;

    public ProjectAccessEntity(
            ProjectRole role,
            ProjectEntity project) {
        this.role = role;
        this.project = project;
        this.grantedAt = Instant.now();
    }
}
