package com.example.pkg2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageMetadataController {
    private final ImageMetadataService imageMetadataService;

    public ImageMetadataController(ImageMetadataService service) {
        this.imageMetadataService = service;
    }

    @PostMapping("/metadata")
    public List<ImageMetadataCustom> getMetadata(@RequestParam("folderPath") String folderPath) {
        System.out.println("Received request for folder: " + folderPath); // Логируем путь к папке
        try {
            Path folder = Paths.get(folderPath);
            return imageMetadataService.getImageMetadata(String.valueOf(folder));
        } catch (IOException e) {
            System.err.println("Error reading metadata: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid folder path", e);
        }
    }
}
