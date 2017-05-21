package pl.com.psl.java8.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by psl on 21.05.17.
 */
public class WatchServiceExample {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchServiceExample.class);
    private static final long SLEEP_BETWEEN_STEPS_MILLIS = 2000;

    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Path directory = Paths.get(".");

            EXECUTOR_SERVICE.execute(() -> {
                LOGGER.info("Starting WatchService thread...");
                WatchService watchService = null;
                try {
                    WatchEvent.Kind<?>[] eventKinds = {
                            StandardWatchEventKinds.ENTRY_CREATE,
                            StandardWatchEventKinds.ENTRY_DELETE,
                            StandardWatchEventKinds.ENTRY_MODIFY};

                    watchService = FileSystems.getDefault().newWatchService();
                    directory.register(watchService, eventKinds);
                    LOGGER.info("Registered WatchService for directory={} with eventKinds={}", directory, eventKinds);

                    while (!Thread.currentThread().isInterrupted()) {
                        LOGGER.info("Waiting for event...");
                        WatchKey watchKey = null;
                        try {
                            watchKey = watchService.take();
                        } catch (InterruptedException ie) {
                            LOGGER.info("Waiting for event interrupted");
                            Thread.currentThread().interrupt();
                            continue;
                        }
                        LOGGER.info("Received WatchKey for Watchable={}", watchKey.watchable());
                        List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                        for (WatchEvent<?> watchEvent : watchEvents) {
                            WatchEvent.Kind<?> kind = watchEvent.kind();
                            LOGGER.info("WatchKey contains WatchEvent of kind={}", kind);
                            if (StandardWatchEventKinds.OVERFLOW.equals(kind)) {
                                LOGGER.error("Oops, something wrong happened, skipping event");
                                continue;
                            }
                            WatchEvent<Path> watchEventWithPath = (WatchEvent<Path>) watchEvent;
                            Path path = watchEventWithPath.context();
                            LOGGER.info("WatchEvent path={}", path);
                        }
                        if (!watchKey.reset()) {
                            LOGGER.info("WatchKey is no longer valid, stopping WatchService");
                            break;
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error("WatchService thread failed", e);
                } finally {
                    LOGGER.info("WatchService thread stopped");
                }
            });

            Thread.sleep(SLEEP_BETWEEN_STEPS_MILLIS);
            Path file = Files.createFile(directory.resolve(Paths.get("some-file.txt")));
            LOGGER.info("Creating file={}", file);
            Thread.sleep(SLEEP_BETWEEN_STEPS_MILLIS);
            String content = "Hello!";
            LOGGER.info("Writing content={} to file={}", content, file);
            Files.write(file, Collections.singletonList(content));
            Thread.sleep(SLEEP_BETWEEN_STEPS_MILLIS);
            LOGGER.info("Deleting file={}", file);
            Files.delete(file);
            Thread.sleep(SLEEP_BETWEEN_STEPS_MILLIS);
            LOGGER.info("Finished all operations in directory={}", directory);
        } finally {
            EXECUTOR_SERVICE.shutdownNow();
        }
    }
}
