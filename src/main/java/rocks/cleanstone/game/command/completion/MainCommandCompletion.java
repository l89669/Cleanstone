package rocks.cleanstone.game.command.completion;

import org.springframework.stereotype.Service;
import rocks.cleanstone.game.command.Command;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class MainCommandCompletion {
    public List<CompletionMatch> completeCommand(String input, Collection<Command> commands) {
        return commands.parallelStream()
                .map(cmd -> commandMatch(input, cmd))
                .filter(Objects::nonNull)
                .collect(toList());
    }

    private CompletionMatch commandMatch(String input, Command command) {
        if (command.getName().startsWith(input)) {
            return new CompletionMatch(command.getName());
        } else {
            return command.getAliases().stream()
                    .filter(alias -> alias.startsWith(input))
                    .findFirst().map(CompletionMatch::new)
                    .orElse(null);
        }
    }
}
