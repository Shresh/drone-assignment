package com.nextdigital.drone.api.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MedicationRequest {
    private Integer id;
    private String name;
    private Float weight;
    private String code;
    private MultipartFile image;
}
