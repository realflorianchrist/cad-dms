package ch.realflorianchrist.caddms.user;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRespository extends Neo4jRepository<UserEntity, UUID> {

}
