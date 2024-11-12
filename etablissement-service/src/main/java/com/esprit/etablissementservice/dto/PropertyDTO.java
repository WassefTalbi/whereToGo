package com.esprit.etablissementservice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Data
public class PropertyDTO {
    private String title;
    private String type;
    private int bedroom;
    private int bathroom;
    private int area;
    private double price;
    private String requirement;
    private String location;
    private List<String> additionalFeatures=new ArrayList<>();
    private MultipartFile image;

}