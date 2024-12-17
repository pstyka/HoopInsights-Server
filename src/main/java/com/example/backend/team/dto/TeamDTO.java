package com.example.backend.team.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TeamDTO {
    private Integer id;
    private String name;
    private String nickname;
    private String abb;
    private String city;
    private String logoUrl;
    private Integer apiId;
}