package pl.com.psl.java8.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * Created by psl on 19.05.17.
 */
public class DirectoryTraversingExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryTraversingExample.class);

    public static void main(String[] args) throws IOException {
        Path currentDirectory = Paths.get(".");
        listDirectory(currentDirectory);
        listDirectoryTreeWithMaxDepth(currentDirectory, 1);
        listDirectoryTreeWithMaxDepth(currentDirectory, 2);
        walkXmlFilesInDirectoryTree(currentDirectory);
        walkDirectoriesInDirectoryTree(currentDirectory);
    }

    private static void listDirectory(Path directory) throws IOException {
        LOGGER.info("Listing paths in directory={}", directory);
        try(Stream<Path> paths = Files.list(directory)) {
            paths.forEach(p -> LOGGER.info("Path=" + p));
        }
    }

    private static void listDirectoryTreeWithMaxDepth(Path directory, int maxDepth) throws IOException {
        LOGGER.info("Listing paths in directory={} to maxDepth={}", directory, maxDepth);
        try(Stream<Path> paths = Files.walk(directory, maxDepth)) {
            paths.forEach(p -> LOGGER.info("Path=" + p));
        }
    }

    private static void walkXmlFilesInDirectoryTree(Path directory) throws IOException {
        LOGGER.info("Walking XML files in directory tree for directory={}", directory);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if("application/xml".equals(Files.probeContentType(file))){
                    LOGGER.info("Visiting file={}", file);
                }
                return super.visitFile(file, attrs);
            }
        });
    }

    private static void walkDirectoriesInDirectoryTree(Path directory) throws IOException {
        LOGGER.info("Walking directories in directory tree for directory={}", directory);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                LOGGER.info("Visiting directory={}", dir);
                return super.preVisitDirectory(dir, attrs);
            }
        });
    }
}
