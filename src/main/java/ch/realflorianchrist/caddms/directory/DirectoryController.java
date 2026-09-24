package ch.realflorianchrist.caddms.directory;

import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DirectoryController {

    private final DirectoryRepository directoryRepository;

    @QueryMapping
    public DirectoryEntity directory(@Argument("directoryId") UUID directoryId) {
        return directoryRepository.findById(directoryId)
                .orElse(null);
    }

}
