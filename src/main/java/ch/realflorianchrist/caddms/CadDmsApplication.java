package ch.realflorianchrist.caddms;

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
import ch.realflorianchrist.caddms.project.ProjectAccessEntity;
import ch.realflorianchrist.caddms.project.ProjectEntity;
import ch.realflorianchrist.caddms.project.ProjectRepository;
import ch.realflorianchrist.caddms.project.ProjectRole;
import ch.realflorianchrist.caddms.user.UserEntity;
import ch.realflorianchrist.caddms.user.UserRespository;

@SpringBootApplication
public class CadDmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadDmsApplication.class, args);
	}

	@Bean
	CommandLineRunner testNeo4j(
			UserRespository userRespository,
			ProjectRepository projectRepository,
			DirectoryRepository directoryRepository,
			DocumentRepository documentRepository,
			DocumentVersionRepository documentVersionRepository) {
		return args -> {
			userRespository.deleteAll();
			projectRepository.deleteAll();
			directoryRepository.deleteAll();
			documentRepository.deleteAll();
			documentVersionRepository.deleteAll();

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

			project.getDocuments().add(document);
			project.getDirectories().add(directory);

			directory.getDirectories().add(directory2);
			directory2.getDirectories().add(directory3);

			document.addVersion(documentVersion2);

			projectRepository.save(project);

			user.getProjectAccess().add(new ProjectAccessEntity(ProjectRole.OWNER, project));

			userRespository.save(user);
		};
	}
}
