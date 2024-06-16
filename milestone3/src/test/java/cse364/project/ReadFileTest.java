package cse364.project;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
public class ReadFileTest {
    @TempDir
    File temporaryDirectory;

    @Test
    public void testReadFile() throws IOException {
        ReadFile rf = new ReadFile();

        File file1 = new File(temporaryDirectory, "file1.txt");
        List<String> lines1 = Arrays.asList("first line", "second line");
        Files.write(file1.toPath(), lines1);

        List<List<String>> readLines = rf.readDAT(file1.getAbsolutePath());
        String readString = readLines.get(0).get(0);
        assertTrue(readLines.size() == 2 && readString.equals("first line"));

        File file2 = new File(temporaryDirectory, "file2.txt");
        List<String> lines2 = Arrays.asList("");
        Files.write(file2.toPath(), lines2);

        assertTrue(rf.readDAT(file2.getAbsolutePath()) instanceof List<List<String>>);
    }

    @Test
    public void testReadFileCatchingException() throws IOException {
        ReadFile rf = new ReadFile();
        assertDoesNotThrow(() -> rf.readDAT("invalid/path"));
    }
}
