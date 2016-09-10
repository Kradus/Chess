package com.game.main;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.game.view.ICommandReader;
import com.game.view.IGameFieldPrinter;

/**
 * This class load all beans for the dependency injection and hold all.
 * 
 * @author Bjoern Hullmann
 */
public enum InjectionLoader {
	/**
	 * The instance (Singleton pattern)
	 */
	INSTANCE;
	
	/**
	 * The BeanFactory which hold all the bean for the dependency injection.
	 */
	private final BeanFactory factory;
	
	/**
	 * Create Injection Loader which load all beans out of xml files.
	 */
	private InjectionLoader() {
		final List<String> xmlFiles = new ArrayList<String>();
		
		final Path injectionFolder = FileSystems.getDefault()
				.getPath(ResourceConst.USER_DIR + '/' + ResourceConst.FOLDER_WITH_INJECTION_FILES);
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(injectionFolder)) {
			for (final Path child : ds) {
				if (child.toString().endsWith(".xml")) {
					xmlFiles.add(
							ResourceConst.FOLDER_WITH_INJECTION_FILES + '/' + child.getFileName());
				}
			}
		} catch (final IOException e) {
			throw new InternalError(e);
		}
		xmlFiles.sort((s1, s2) -> s1.compareTo(s2));
		factory = new FileSystemXmlApplicationContext(
				xmlFiles.toArray(new String[xmlFiles.size()]));
	}
	
	/**
	 * Get a instance for a given class, which is set as bean for the dependency injection
	 * 
	 * @param <T>
	 *            The required class
	 * @param requiredType
	 *            the class from which you want a instance
	 * @return a instance of the given class.
	 */
	public <T> T getInstanceOf(Class<T> requiredType) {
		return factory.getBean(requiredType);
	}
	
	/**
	 * The method to start the program
	 * 
	 * @param args
	 *            parameters that has no used here.
	 */
	public static void main(final String[] args) {
		final ICommandReader commandReader = InjectionLoader.INSTANCE
				.getInstanceOf(ICommandReader.class);
		// Print the first game field.
		InjectionLoader.INSTANCE.getInstanceOf(IGameFieldPrinter.class).update(null, null);
		commandReader.start();
	}
}
