package ch.realflorianchrist.caddms.metadata;

import java.util.ArrayList;
import java.util.List;

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
public class MetadataBinding {

    @RelationshipId
    private Long id;

    @TargetNode
    private MetadataDefinitionEntity definition;

    private List<MetadataTargetType> targetTypes = new ArrayList<>();

    private MetadataReach reach;

    private boolean required;

    /**
     * Text representation according to the definition's type; null means no
     * default.
     */
    private String defaultValue;

    public MetadataBinding(MetadataDefinitionEntity definition,
            List<MetadataTargetType> targetTypes, MetadataReach reach) {
        this.definition = definition;
        this.targetTypes = new ArrayList<>(targetTypes);
        this.reach = reach;
    }
}
