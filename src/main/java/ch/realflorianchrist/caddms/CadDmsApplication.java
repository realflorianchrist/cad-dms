package ch.realflorianchrist.caddms;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ch.realflorianchrist.caddms.directory.DirectoryEntity;
import ch.realflorianchrist.caddms.directory.DirectoryRepository;
import ch.realflorianchrist.caddms.document.DocumentEntity;
import ch.realflorianchrist.caddms.document.DocumentRepository;
import ch.realflorianchrist.caddms.document.DocumentVersionEntity;
import ch.realflorianchrist.caddms.document.DocumentVersionRepository;
import ch.realflorianchrist.caddms.metadata.MetadataBinding;
import ch.realflorianchrist.caddms.metadata.MetadataDefinitionEntity;
import ch.realflorianchrist.caddms.metadata.MetadataDefinitionRepository;
import ch.realflorianchrist.caddms.metadata.MetadataReach;
import ch.realflorianchrist.caddms.metadata.MetadataTargetType;
import ch.realflorianchrist.caddms.metadata.MetadataType;
import ch.realflorianchrist.caddms.metadata.MetadataValue;
import ch.realflorianchrist.caddms.project.ProjectAccessEntity;
import ch.realflorianchrist.caddms.project.ProjectEntity;
import ch.realflorianchrist.caddms.project.ProjectRepository;
import ch.realflorianchrist.caddms.project.ProjectRole;
import ch.realflorianchrist.caddms.user.UserEntity;
import ch.realflorianchrist.caddms.user.UserRepository;

@SpringBootApplication
public class CadDmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadDmsApplication.class, args);
	}

	@Bean
	CommandLineRunner testNeo4j(
			UserRepository userRepository,
			ProjectRepository projectRepository,
			DirectoryRepository directoryRepository,
			DocumentRepository documentRepository,
			DocumentVersionRepository documentVersionRepository,
			MetadataDefinitionRepository metadataDefinitionRepository) {
		return args -> {
			userRepository.deleteAll();
			projectRepository.deleteAll();
			directoryRepository.deleteAll();
			documentRepository.deleteAll();
			documentVersionRepository.deleteAll();
			metadataDefinitionRepository.deleteAll();

			var user = new UserEntity(
					"test-identity-provider",
					"test-external-subject-id",
					"Test User",
					true);

			var project = new ProjectEntity("test-project", user);

			var documentVersion = new DocumentVersionEntity(1,
					"terrain",
					"3dm",
					"contentHash",
					"storageKey",
					1024L,
					user);

			var directory = new DirectoryEntity("test-directory", user);
			var directory2 = new DirectoryEntity("test-directory-2", user);
			var directory3 = new DirectoryEntity("test-directory-3", user);

			var document = new DocumentEntity(user, documentVersion);

			var documentVersion2 = new DocumentVersionEntity(2,
					"house",
					"dwg",
					"contentHash2",
					"storageKey2",
					1024L,
					user);

			directory3.getDocuments().add(document);
			project.getDirectories().add(directory);

			directory.getDirectories().add(directory2);
			directory2.getDirectories().add(directory3);

			document.addVersion(documentVersion2);

			// SELF: Ein Feld mit eigenem Wert direkt am Projekt.
			var projectNumber = new MetadataDefinitionEntity("projectNumber", "Projektnummer", MetadataType.TEXT);
			project.getMetadataBindings().add(
					new MetadataBinding(projectNumber,
							List.of(MetadataTargetType.PROJECT),
							MetadataReach.SELF));
			project.getMetadataValues().add(new MetadataValue(projectNumber, "P-2026-001"));

			// DESCENDANTS: Dieses Feld gilt fuer alle Verzeichnisse im Projekt.
			var building = new MetadataDefinitionEntity("building", "Gebaeude", MetadataType.TEXT);
			var buildingBinding = new MetadataBinding(
					building,
					List.of(MetadataTargetType.DIRECTORY),
					MetadataReach.DESCENDANTS);
			buildingBinding.setDefaultValue("Hauptgebaeude");
			project.getMetadataBindings().add(buildingBinding);
			directory2.getMetadataValues().add(new MetadataValue(building, "Nebengebaeude"));

			// Dokumente im gesamten Teilbaum von directory erhalten dieses Binding.
			// Mit CHILDREN wuerde es nur fuer direkt enthaltene Dokumente gelten.
			var discipline = new MetadataDefinitionEntity("discipline", "Gewerk", MetadataType.ENUM);
			discipline.setOptions(List.of("Architektur", "Elektro", "Sanitaer"));
			var disciplineBinding = new MetadataBinding(
					discipline,
					List.of(MetadataTargetType.DOCUMENT),
					MetadataReach.DESCENDANTS);
			disciplineBinding.setRequired(true);
			disciplineBinding.setDefaultValue("Elektro");
			directory.getMetadataBindings().add(disciplineBinding);

			// Das Dokument in directory3 hat einen eigenen Wert statt des Standardwerts.
			document.getMetadataValues().add(new MetadataValue(discipline, "Architektur"));

			// Speichert auch die Definitionen und beide Arten von Metadata-Relationships.
			// Vererbung und Pflichtfeldpruefung sind bisher nur modelliert, nicht
			// implementiert.
			projectRepository.save(project);

			user.getProjectAccess().add(new ProjectAccessEntity(ProjectRole.OWNER, project));

			userRepository.save(user);
		};
	}
}
