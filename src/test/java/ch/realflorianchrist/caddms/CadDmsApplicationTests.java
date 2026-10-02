package ch.realflorianchrist.caddms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import ch.realflorianchrist.caddms.directory.DirectoryEntity;
import ch.realflorianchrist.caddms.directory.DirectoryRepository;
import ch.realflorianchrist.caddms.directory.DirectoryRole;
import ch.realflorianchrist.caddms.metadata.MetadataTargetType;
import ch.realflorianchrist.caddms.user.UserRepository;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class CadDmsApplicationTests {

    @Autowired
    DirectoryRepository directoryRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void loadsOnlyRootsWithTheirHierarchyAndRelatedData() {
        var roots = directoryRepository.findRootDirectories();
        assertThat(roots).singleElement().satisfies(root -> {
            assertThat(root.getName()).isEqualTo("test-project");
            assertThat(root.getCreatedBy().getDisplayName()).isEqualTo("Test User");
            assertThat(root.getMetadataBindings()).hasSize(2);
            assertThat(root.getMetadataValues()).singleElement().satisfies(value -> {
                assertThat(value.getDefinition().getKey()).isEqualTo("projectNumber");
                assertThat(value.getValue()).isEqualTo("P-2026-001");
            });
            assertThat(root.getDirectories()).hasSize(1);
            var child = root.getDirectories().getFirst();
            assertThat(child.getCreatedBy()).isNotNull();
            assertThat(child.getMetadataBindings()).singleElement().satisfies(binding -> {
                assertThat(binding.getDefinition().getKey()).isEqualTo("discipline");
                assertThat(binding.getDefinition().getOptions())
                        .containsExactly("Architektur", "Elektro", "Sanitaer");
                assertThat(binding.isRequired()).isTrue();
                assertThat(binding.getDefaultValue()).isEqualTo("Elektro");
            });
            var grandchild = child.getDirectories().getFirst();
            assertThat(grandchild.getMetadataValues()).singleElement().satisfies(value ->
                    assertThat(value.getValue()).isEqualTo("Nebengebaeude"));
            var document = grandchild.getDirectories().getFirst().getDocuments().getFirst();
            assertThat(document.getCreatedBy()).isNotNull();
            assertThat(document.getVersions()).hasSize(2);
            assertThat(document.getCurrentVersion().getNumber()).isEqualTo(2);
            assertThat(document.getCurrentVersion().getCreatedBy()).isNotNull();
            assertThat(document.getMetadataValues()).singleElement().satisfies(value -> {
                assertThat(value.getDefinition().getKey()).isEqualTo("discipline");
                assertThat(value.getValue()).isEqualTo("Architektur");
            });
        });

        var empty = directoryRepository.save(new DirectoryEntity("empty-root", roots.getFirst().getCreatedBy()));
        try {
            var loadedRoots = directoryRepository.findRootDirectories();
            assertThat(loadedRoots).extracting(DirectoryEntity::getDirectoryId)
                    .containsExactlyInAnyOrder(roots.getFirst().getDirectoryId(), empty.getDirectoryId());
            var loaded = loadedRoots.stream()
                    .filter(directory -> directory.getDirectoryId().equals(empty.getDirectoryId()))
                    .findFirst().orElseThrow();
            assertThat(loaded.getCreatedBy()).isNotNull();
            assertThat(loaded.getDirectories()).isEmpty();
            assertThat(loaded.getDocuments()).isEmpty();
            assertThat(loaded.getMetadataBindings()).isEmpty();
            assertThat(loaded.getMetadataValues()).isEmpty();
        } finally {
            directoryRepository.deleteById(empty.getDirectoryId());
        }
    }
    @Test
    void storesProjectMetadataAndAccessOnAnOrdinaryDirectory() {
        var root = directoryRepository.findAll().stream()
                .filter(directory -> directory.getName().equals("test-project"))
                .findFirst().orElseThrow();
        assertThat(root.getDirectories()).hasSize(1);
        assertThat(root.getDirectories().getFirst().getDirectories().getFirst()
                .getDirectories().getFirst().getDocuments()).hasSize(1);
        assertThat(root.getMetadataValues()).anySatisfy(value -> {
            assertThat(value.getDefinition().getKey()).isEqualTo("projectNumber");
            assertThat(value.getValue()).isEqualTo("P-2026-001");
        });
        assertThat(root.getMetadataBindings()).anySatisfy(binding -> {
            assertThat(binding.getDefinition().getKey()).isEqualTo("projectNumber");
            assertThat(binding.getTargetTypes()).containsExactly(MetadataTargetType.DIRECTORY);
        });
        assertThat(userRepository.findAll()).singleElement().satisfies(user ->
                assertThat(user.getDirectoryAccess()).singleElement().satisfies(access -> {
                    assertThat(access.getId()).isNotNull();
                    assertThat(access.getRole()).isEqualTo(DirectoryRole.OWNER);
                    assertThat(access.getDirectory().getDirectoryId()).isEqualTo(root.getDirectoryId());
                }));
    }
}
