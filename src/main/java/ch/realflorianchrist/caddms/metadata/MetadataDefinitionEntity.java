package ch.realflorianchrist.caddms.metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Node("MetadataDefinition")
public class MetadataDefinitionEntity {

    @Id
    private UUID metadataDefinitionId;

    @Version
    private Long persistenceVersion;

    private String key;

    private String label;

    private MetadataType type;

    private List<String> options = new ArrayList<>();

    public MetadataDefinitionEntity(String key, String label, MetadataType type) {
        this.metadataDefinitionId = UUID.randomUUID();
        this.key = key;
        this.label = label;
        this.type = type;
    }
}
