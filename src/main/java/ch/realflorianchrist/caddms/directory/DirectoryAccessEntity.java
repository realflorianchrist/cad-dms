package ch.realflorianchrist.caddms.directory;

import java.time.Instant;

import org.springframework.data.neo4j.core.schema.RelationshipId;
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
public class DirectoryAccessEntity {

    @RelationshipId
    private Long id;

    private DirectoryRole role;

    private Instant grantedAt;

    @TargetNode
    private DirectoryEntity directory;

    public DirectoryAccessEntity(
            DirectoryRole role,
            DirectoryEntity directory) {
        this.role = role;
        this.directory = directory;
        this.grantedAt = Instant.now();
    }
}
