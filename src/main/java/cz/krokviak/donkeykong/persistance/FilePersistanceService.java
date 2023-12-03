package cz.krokviak.donkeykong.persistance;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class FilePersistanceService implements PersistanceService{
    private static final Path FILE_PATH = Path.of("scores.txt");
    @Override
    public void save(final List<GameScore> scores) {
        try(final FileWriter fileWriter = new FileWriter(FILE_PATH.toFile())){
            for (final GameScore score : scores) {
                fileWriter.write(score.toString() + "\n");
            }
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addScore(GameScore score) {
        try(final FileWriter fileWriter = new FileWriter(FILE_PATH.toFile(), true)){
            fileWriter.write(score.toString() + "\n");
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameScore> load() {
        try(final Stream<String> lines = Files.lines(FILE_PATH)){
            return lines
                    .map(line -> {
                        final String[] parts = line.split(" ");
                        return new GameScore(parts[0], Integer.parseInt(parts[1]));
                    })
                    .toList();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
