package no.ntnu.rentalroulette.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import no.ntnu.rentalroulette.service.StorageService;
import no.ntnu.rentalroulette.exception.StorageException;
import no.ntnu.rentalroulette.exception.StorageFileNotFoundException;

@Service
public class FileSystemStorageService implements StorageService {

  private Path rootLocation;

  public FileSystemStorageService() throws StorageException {
    Path uploadFolder = Paths.get("user-uploads");
    if (!Files.exists(uploadFolder)) {
      try {
        Files.createDirectories(uploadFolder);
      } catch (IOException exception) {
        System.out.println(exception.getMessage());
        throw new StorageException("Failed to create root dir");
      }
    }
    rootLocation = uploadFolder;
  }

  public Path getRootLocation() {
    return this.rootLocation;
  }

  @Override
  public Path store(MultipartFile file) {
    System.out.println(rootLocation);
    Path destinationFile = null;
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }
      String filename = file.getOriginalFilename();
      if (filename == null || filename.isEmpty()) {
        throw new StorageException("File name cannot be null or empty.");
      }
      // Validate file extension
      String lowerCaseFilename = filename.toLowerCase();
      if (!(lowerCaseFilename.endsWith(".png") || lowerCaseFilename.endsWith(".jpg") ||
          lowerCaseFilename.endsWith(".jpeg"))) {
        throw new StorageException("Invalid file type. Only .png, .jpg, and .jpeg are allowed.");
      }
      destinationFile = this.rootLocation.resolve(
              Paths.get(file.getOriginalFilename()))
          .normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new StorageException(
            "Cannot store file outside current directory.");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }

    System.out.println("File stored at: " + destinationFile);
    System.out.println(this.rootLocation.relativize(destinationFile));
    return this.rootLocation.relativize(destinationFile);
  }

  @Override
  public Stream<Path> loadAll() {
    try {
      return Files.walk(this.rootLocation, 1)
          .filter(path -> !path.equals(this.rootLocation))
          .map(this.rootLocation::relativize);
    } catch (IOException e) {
      throw new StorageException("Failed to read stored files", e);
    }

  }

  @Override
  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
}
