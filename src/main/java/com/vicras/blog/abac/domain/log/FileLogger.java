package com.vicras.blog.abac.domain.log;

import static com.vicras.blog.constants.TimeConstants.DATE_FORMATTER;
import static com.vicras.blog.constants.TimeConstants.DATE_TIME_FORMATTER;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.TimeUnit.SECONDS;

import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileLogger {

    private static final String DIRECTORY_PATH = "logs";
    volatile boolean isAllowToCreate = true;
    private final Queue<String> logs = new ConcurrentLinkedQueue<>();
    private final ScheduledExecutorService scheduler = newScheduledThreadPool(2);

    @PostConstruct
    private void createLogDirectories() {
        var logDirectoryPath = Path.of(DIRECTORY_PATH);
        isAllowToCreate = Try.of(() -> Files.createDirectory(logDirectoryPath))
                .map(nil -> true)
                .recover(FileAlreadyExistsException.class, true)
                .getOrElse(false);
        scheduler.scheduleAtFixedRate(this::writeLogsToFile, 10, 20, SECONDS);
    }

    public void logInfo(String text) {
        logs.add(createLog(text));
    }

    private void writeLogsToFile() {
        if (!logs.isEmpty() && isAllowToCreate) {
            var logText = String.join("\n", logs);
            CheckedRunnable writeToFile = () -> Files.write(
                    pathForSavingLog(),
                    logText.getBytes(UTF_8),
                    APPEND);
            Consumer<Throwable> recover = (nil) -> Try
                    .run(() -> Files.createFile(pathForSavingLog()))
                    .andThenTry(writeToFile);
            Try.run(writeToFile)
                    .onFailure(NoSuchFileException.class, recover)
                    .onSuccess((nil) -> logs.clear());
        }
    }

    private Path pathForSavingLog() {
        return Path.of(DIRECTORY_PATH, fileName());
    }

    private String createLog(String text) {
        var time = time();
        return format("%20s %s", time, text);
    }

    private String fileName() {
        return format("%s.txt", LocalDate.now().format(DATE_FORMATTER));
    }

    private String time() {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER);
    }
}
