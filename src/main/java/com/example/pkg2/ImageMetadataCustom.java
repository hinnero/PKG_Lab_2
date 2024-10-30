package com.example.pkg2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageMetadataCustom {
    private String fileName;

    private int width;

    private int height;

    private int dpi;

    private int colorDepth;

    private String compression;
}