package com.smart.bean;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

public class muFile implements Serializable {
    private String description;
    private MultipartFile file;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
