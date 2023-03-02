package ro.fortech.betting.service;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.stereotype.Service;

import ro.fortech.betting.service.events.OddsChangeEvent;

@Service
public class OddsChangeListenerService {

	private final ApplicationEventPublisher eventPublisher;

	public OddsChangeListenerService(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}

	@Bean
	ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
		eventMulticaster.setErrorHandler(TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);
		return eventMulticaster;
	}

	public void listenForChanges() {
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		singleThreadExecutor.execute(() -> {
			try {

				WatchService watchService = FileSystems.getDefault().newWatchService();
				Path folder = Paths.get("D:\\training-spring\\betting\\src\\main\\resources");
				folder.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

				WatchKey key;
				while ((key = watchService.take()) != null) {
					for (WatchEvent<?> event : key.pollEvents()) {
						WatchEvent.Kind<?> kind = event.kind();

						if (kind == StandardWatchEventKinds.OVERFLOW) {
							continue;
						}

						WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
						Path path = folder.resolve(pathEvent.context());

						String content = "";

						try (Stream<String> stream = Files.lines(path)) {

							content = stream.findFirst().get();

						} catch (IOException e) {
							System.out.println(e.getMessage());
							e.printStackTrace();
						}

						eventPublisher.publishEvent(new OddsChangeEvent(this, content));
					}

					boolean valid = key.reset();
					if (!valid) {
						break;
					}
				}

				watchService.close();
			} catch (Exception e) {
				System.out.println("Error watching service for reading odds");
			}
		});
	}

}
