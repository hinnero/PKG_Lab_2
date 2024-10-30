package com.example.pkg2;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImageMetadataService {

    private static final Logger log = LoggerFactory.getLogger(ImageMetadataService.class);

    public List<ImageMetadataCustom> getImageMetadata(String folderPath) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        List<ImageMetadataCustom> metadataList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (!isImageFile(file)) continue;
                log.info("Reading image file {}", file.getAbsolutePath());

                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    String compression = getCompression(file);
                    int dpi = getDpi(file);

                    ImageMetadataCustom metadata = new ImageMetadataCustom(
                            file.getName(),
                            image.getWidth(),
                            image.getHeight(),
                            dpi,
                            image.getColorModel().getPixelSize(),
                            compression
                    );
                    metadataList.add(metadata);
                }
            }
        }
        return metadataList;
    }

    private boolean isImageFile(File file) {
        String[] imageExtensions = { "jpg", "jpeg", "png", "gif", "bmp", "tif", "tiff", "pcx" };
        String fileName = file.getName().toLowerCase();
        for (String ext : imageExtensions) {
            if (fileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private String getCompression(File file) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (tag.getTagName().equalsIgnoreCase("Compression Type")) {
                        return tag.getDescription();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    private int getDpi(File file) {
        try {
            ImageInfo info = Sanselan.getImageInfo(file);
            return info.getPhysicalWidthDpi();
        } catch (IOException | ImageReadException e) {
            e.printStackTrace();
        }
        return -1;
    }

}