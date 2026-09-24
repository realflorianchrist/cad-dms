package ch.realflorianchrist.caddms.user;

import java.util.UUID;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<UserEntity, UUID> {

}
