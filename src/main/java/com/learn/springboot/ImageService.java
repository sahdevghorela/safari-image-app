package com.learn.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Service
public class ImageService {

    private static String UPLOAD_ROOT = "upload-dir";

    private final ImageRepository repository;
    private final ResourceLoader resourceLoader;

    @Autowired
    public ImageService(ImageRepository repository, ResourceLoader resourceLoader) {
        this.repository = repository;
        this.resourceLoader = resourceLoader;
    }

    public Page<Image> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Resource findOneImage(String fileName) {
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + fileName);
    }

    public void createImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
            repository.save(new Image(file.getOriginalFilename()));
        }
    }

    public void deleteImage(String fileName) throws IOException {
        final Image byName = repository.findByName(fileName);
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, fileName));
        repository.delete(byName);
    }

    @Bean
    CommandLineRunner setUp(ImageRepository repository, ConditionEvaluationReport conditionEvaluationReport) throws IOException {
        return args -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectory(Paths.get(UPLOAD_ROOT));

            FileCopyUtils.copy("Test File", new FileWriter(UPLOAD_ROOT + "/test.txt"));
            repository.save(new Image("test"));

            FileCopyUtils.copy("Test File2", new FileWriter(UPLOAD_ROOT + "/test2"));
            repository.save(new Image("test2"));

            FileCopyUtils.copy("Test File3", new FileWriter(UPLOAD_ROOT + "/test3"));
            repository.save(new Image("test3"));

            conditionEvaluationReport.getConditionAndOutcomesBySource()
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().isFullMatch())
                    .forEach(entry -> System    .out.println(entry.getKey() + "=> isMatch?"+entry.getValue().isFullMatch()));
        };

    }
}
