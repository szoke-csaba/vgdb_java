package io.github.szokecsaba.vgdb.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Component
public class FileHandlerUtil {
    private String fileName;
    private String directory;
    private MultipartFile multipartFile;

    public void setData(MultipartFile multipartFile, String directory) {
        this.multipartFile = multipartFile;
        this.directory = directory;
        this.fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        this.fileName = System.currentTimeMillis() + "_" + this.fileName;
    }

    public void saveFile() throws IOException {
        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image: " + fileName, ioe);
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void delete(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
