package pl.com.psl.java8.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by psl on 15.05.17.
 */
public class BasicPathsAndFilesExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicPathsAndFilesExample.class);

    public static void main(String[] args) {
        Path nonExistingPath = Paths.get("itDoesNotExist.txt");
        Path movePath = nonExistingPath.resolve(Paths.get("..", "nowItDoesExist.txt")).toAbsolutePath().normalize();
        Path copyPath = Paths.get("..", "copy-" + movePath.getFileName());
        log(nonExistingPath, movePath, copyPath);

        try{
            LOGGER.info("Creating path={}", nonExistingPath);
            Files.createFile(nonExistingPath);
            log(nonExistingPath);

            LOGGER.info("Moving path={} to path={}", nonExistingPath, movePath);
            Files.move(nonExistingPath, movePath);
            log(nonExistingPath, movePath);

            Instant writeInstant = Instant.now();
            String fileContent = "Hello at " + writeInstant;
            LOGGER.info("Writing content={} to path={}", fileContent, movePath);
            LOGGER.info("Size before writing={} bytes, last modification time={}", Files.size(movePath), Files.getLastModifiedTime(movePath));
            try(BufferedWriter writer = Files.newBufferedWriter(movePath)){
                writer.write(fileContent);
            }
            Files.setLastModifiedTime(movePath, FileTime.from(writeInstant.plusSeconds(1)));
            LOGGER.info("Size after writing={} bytes, last modification time={}, content={}",
                    Files.size(movePath), Files.getLastModifiedTime(movePath), Files.lines(movePath).collect(Collectors.toList()));

            LOGGER.info("Making a shallow copy from path={} to path={}", movePath, copyPath);
            Files.copy(movePath, copyPath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("Size after copying={} bytes, last modification time={}", Files.size(copyPath), Files.getLastModifiedTime(copyPath));

            LOGGER.info("Reading content of copied path={}:", copyPath);
            List<String> copiedLines = Files.readAllLines(copyPath);
            copiedLines.forEach(LOGGER::info);

            LOGGER.info("Original and copied file point to the same path={}", Files.isSameFile(movePath, copyPath));

            fileContent = " Hello at " + Instant.now();
            LOGGER.info("Appending content={} to path={}", fileContent, copyPath);
            Files.write(copyPath, Arrays.asList(fileContent), StandardOpenOption.APPEND);
            LOGGER.info("Size after writing={} bytes, last modification time={}, content={}",
                    Files.size(copyPath), Files.getLastModifiedTime(copyPath), Files.lines(copyPath).collect(Collectors.toList()));

            Path copyAbsolutePath = copyPath.toAbsolutePath().normalize();
            LOGGER.info("Root of path={} is={}", copyAbsolutePath, copyAbsolutePath.getRoot());
            LOGGER.info("Parent of path={} is={}", copyAbsolutePath, copyAbsolutePath.getParent());
            // subpath skips root!
            LOGGER.info("Sub-paths of path={}:{}", copyAbsolutePath,
                    IntStream.range(1, copyAbsolutePath.getNameCount() + 1)
                            .mapToObj(i -> copyAbsolutePath.subpath(0, i))
                            .collect(Collectors.toList()));

            BasicFileAttributes attributes = Files.readAttributes(copyAbsolutePath, BasicFileAttributes.class);
            LOGGER.info("Got all attributes for path={} in one fetch-> size={}, creation time={} last access time={}, last modified time={}",
                    copyAbsolutePath, attributes.size(), attributes.creationTime(), attributes.lastAccessTime(), attributes.lastModifiedTime());

            BasicFileAttributeView attributeView = Files.getFileAttributeView(copyAbsolutePath, BasicFileAttributeView.class);
            FileTime now = FileTime.from(Instant.now().plusSeconds(1));
            LOGGER.info("Using attributes view to set all time values for path={} to now={}", copyAbsolutePath, now);
            attributeView.setTimes(now, now, now);

            attributes = Files.readAttributes(copyAbsolutePath, BasicFileAttributes.class);
            LOGGER.info("Got all attributes for path={} in one fetch-> size={}, creation time={} last access time={}, last modified time={}",
                    copyAbsolutePath, attributes.size(), attributes.creationTime(), attributes.lastAccessTime(), attributes.lastModifiedTime());
        }
        catch(Exception e){
            LOGGER.error("Exception occurred",e);
        }
        finally {
            LOGGER.info("Running cleanup...");
            delete(true,nonExistingPath, movePath, copyPath, nonExistingPath);
            delete(false, nonExistingPath);
            LOGGER.info("Cleanup finished");
        }
    }

    private static void delete(boolean onlyIfExists, Path... paths){
        Arrays.stream(paths).forEach(path -> {
            LOGGER.info("Deleting path={} {}", path, onlyIfExists ? "only if it exists" : "even if it does not exist");
            try{
                if(onlyIfExists){
                    Files.deleteIfExists(path);
                }
                else{
                    Files.delete(path);
                }
            }
            catch (IOException ioe){
                LOGGER.error("Failed to remove path={} due to exception={}:{}!", path, ioe.getClass().getName(),ioe.getMessage());
            }
        });
    }

    private static void log(Path... paths){
        Arrays.stream(paths).forEach( path -> {
            String owner = "-";
            try{
                owner = Files.getOwner(path).getName();
            }
            catch (Exception e){
                //do nothing
            }
            LOGGER.info("Path={}({}) with properties=" +
                            "[exists={}, owner={}, file={}, directory={}, symlink={}]",
                    path, path.toAbsolutePath().normalize(),
                    Files.exists(path), owner, Files.isRegularFile(path), Files.isDirectory(path), Files.isSymbolicLink(path));
                }
        );
    }
}
