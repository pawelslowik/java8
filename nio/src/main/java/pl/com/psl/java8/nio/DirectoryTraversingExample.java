package pl.com.psl.java8.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UncheckedIOException;
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
        listXmlFilesInDirectory(currentDirectory);
        listDirectoryTreeWithMaxDepth(currentDirectory, 1);
        listDirectoryTreeWithMaxDepth(currentDirectory, 2);
        walkXmlFilesInDirectoryTree(currentDirectory);
        walkClassFilesInDirectoryTree(currentDirectory);
        walkDirectoriesInDirectoryTree(currentDirectory);

        Path classesDirectory = currentDirectory.resolve("target").resolve("classes");
        Path classesCopyDirectory = currentDirectory.resolve("target").resolve("copy-classes");

        copyDirectoryTree(classesDirectory, classesCopyDirectory);
        removeDirectoryTree(classesCopyDirectory);
    }

    private static void listDirectory(Path directory) throws IOException {
        LOGGER.info("Listing paths in directory={}", directory);
        try (Stream<Path> paths = Files.list(directory)) {
            paths.forEach(p -> LOGGER.info("Path={}", p));
        }
    }

    private static void listXmlFilesInDirectory(Path directory) throws IOException {
        LOGGER.info("Listing xml files directory={}", directory);
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory, "*.xml")){
            directoryStream.forEach( p -> LOGGER.info("Found xml file={}", p));
        }
    }

    private static void listDirectoryTreeWithMaxDepth(Path directory, int maxDepth) throws IOException {
        LOGGER.info("Listing paths in directory={} to maxDepth={}", directory, maxDepth);
        try (Stream<Path> paths = Files.walk(directory, maxDepth)) {
            paths.forEach(p -> LOGGER.info("Path={}", p));
        }
    }

    private static void walkXmlFilesInDirectoryTree(Path directory) throws IOException {
        LOGGER.info("Walking xml files in directory tree under directory={}", directory);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if ("application/xml".equals(Files.probeContentType(file))) {
                    LOGGER.info("Visiting xml file={}", file);
                }
                return super.visitFile(file, attrs);
            }
        });
    }

    private static void walkClassFilesInDirectoryTree(Path directory) throws IOException {
        LOGGER.info("Walking class files in directory tree under directory={}", directory);
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:**.class");
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (pathMatcher.matches(file)) {
                    LOGGER.info("Visiting class file={}", file);
                }
                return super.visitFile(file, attrs);
            }
        });
    }

    private static void walkDirectoriesInDirectoryTree(Path directory) throws IOException {
        LOGGER.info("Walking directories in directory tree under directory={}", directory);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                LOGGER.info("Visiting directory={}", dir);
                return super.preVisitDirectory(dir, attrs);
            }
        });
    }

    private static void copyDirectoryTree(Path source, Path target) throws IOException {
        LOGGER.info("Copying directory tree from source={} to target={}", source, target);
        Files.walk(source).forEach(
                sourcePath -> {
                    Path targetPath = target.resolve(source.relativize(sourcePath));
                    LOGGER.info("Copying sourcePath={} targetPath={}", sourcePath, targetPath);
                    try {
                        Files.copy(sourcePath, targetPath);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
        );
    }

    private static void removeDirectoryTree(Path directory) throws IOException {
        LOGGER.info("Removing directory tree under directory={}", directory);
        Files.walkFileTree(directory, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                LOGGER.info("Entering directory={}", dir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                LOGGER.info("Removing file={}", file);
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                LOGGER.error("Failed to visit file", exc);
                return FileVisitResult.TERMINATE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                LOGGER.info("Leaving and removing directory={}", dir);
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
