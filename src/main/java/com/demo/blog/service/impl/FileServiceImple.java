package com.demo.blog.service.impl;

import com.demo.blog.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImple implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile image) throws IOException {

        //File name
        //abc.png
        String name = image.getOriginalFilename();

        //random name generator
        String randomID = UUID.randomUUID().toString();
        String filename = randomID.concat(name.substring(name.lastIndexOf(".")));

        //Full path
        String fullPath= path + File.separator +filename;

        //Creating new folder if doesn't exist
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //File Copy
        Files.copy(image.getInputStream(), Paths.get(fullPath));
        return filename;
    }

    @Override
    public InputStream getResourceImage(String path, String fileName) throws FileNotFoundException {

        String fullPath= path + File.separator +fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
