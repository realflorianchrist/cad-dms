package ch.realflorianchrist.caddms.metadata;

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
public class MetadataValue {

    @RelationshipId
    private Long id;

    @TargetNode
    private MetadataDefinitionEntity definition;

    /** Text representation according to the definition's type. */
    private String value;

    public MetadataValue(MetadataDefinitionEntity definition, String value) {
        this.definition = definition;
        this.value = value;
    }
}
