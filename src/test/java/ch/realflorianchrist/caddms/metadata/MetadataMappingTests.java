package ch.realflorianchrist.caddms.metadata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.data.neo4j.core.mapping.Neo4jMappingContext;

import ch.realflorianchrist.caddms.directory.DirectoryEntity;
import ch.realflorianchrist.caddms.document.DocumentEntity;
import ch.realflorianchrist.caddms.project.ProjectEntity;

class MetadataMappingTests {

    @Test
    void mapsBindingsAndValuesAsRelationshipsToSharedDefinitions() {
        var context = new Neo4jMappingContext();

        for (var owner : List.of(ProjectEntity.class, DirectoryEntity.class, DocumentEntity.class)) {
            var entity = context.getRequiredPersistentEntity(owner);

            for (var relationshipType : List.of("HAS_METADATA", "HAS_METADATA_VALUE")) {
                var relationships = entity.getRelationships().stream()
                        .filter(relationship -> relationshipType.equals(relationship.getType()))
                        .toList();
                assertThat(relationships).hasSize(1);
                var relationship = relationships.getFirst();
                assertThat(relationship.getTarget().getUnderlyingClass())
                        .isEqualTo(MetadataDefinitionEntity.class);
                assertThat(relationship.hasRelationshipProperties()).isTrue();
                assertThat(relationship.isOutgoing()).isTrue();
                assertThat(relationship.getRequiredRelationshipPropertiesEntity().getUnderlyingClass())
                        .isEqualTo(relationshipType.equals("HAS_METADATA")
                                ? MetadataBinding.class : MetadataValue.class);
            }
        }
    }
}
