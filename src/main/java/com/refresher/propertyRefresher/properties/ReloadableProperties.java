package com.refresher.propertyRefresher.properties;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.Cleanup;

public abstract class ReloadableProperties {

@Autowired
protected StandardEnvironment environment;
private long lastModTime = 0L;
private Path configPath = null;
private PropertySource<?> appConfigPropertySource = null;
/**
 * 		This will search in environment properties for "application.properties"
 *  	From spring-boot 2.4.0 config file processing has been changed:
 *  	In order to use old processing, please add the following line to
 *  application.properties: "spring.config.use-legacy-processing: true"
 *  	For further information, please check: 
 *  	https://spring.io/blog/2020/08/14/config-file-processing-in-spring-boot-2-4
 */
@PostConstruct
private void stopIfProblemsCreatingContext() {
	System.out.println("reloading");
	MutablePropertySources propertySources = environment.getPropertySources();

	Optional<PropertySource<?>> appConfigPsOp = StreamSupport.stream(propertySources.spliterator(), false)
			.filter(ps -> ps.getName().matches("^.*applicationConfig.*file:.*$")).findFirst();
	if (!appConfigPsOp.isPresent()) {
		// this will stop context initialization
		// (i.e. kill the spring boot program before it initializes)
		throw new RuntimeException("Unable to find property Source as file");
	}
	appConfigPropertySource = appConfigPsOp.get();

	String filename = appConfigPropertySource.getName();
	filename = filename.replace("applicationConfig: [file:", "").replaceAll("\\]$", "");

	configPath = Paths.get(filename);

}

@Scheduled(fixedRate = 2000)
private void reload() throws IOException {
	System.out.println("reloading...");
	long currentModTs = Files.getLastModifiedTime(configPath).toMillis();
	if (currentModTs > lastModTime) {
		lastModTime = currentModTs;
		Properties properties = new Properties();
		@Cleanup
		InputStream inputStream = Files.newInputStream(configPath);
		properties.load(inputStream);
		environment.getPropertySources().replace(appConfigPropertySource.getName(),
				new PropertiesPropertySource(appConfigPropertySource.getName(), properties));
		System.out.println("Reloaded.");
		propertiesReloaded();
	}
}

 protected abstract void propertiesReloaded();
}