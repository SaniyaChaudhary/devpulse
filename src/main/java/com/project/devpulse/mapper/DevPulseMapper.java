package com.project.devpulse.mapper;

import com.project.devpulse.dto.DevPulseResponseDTO;
import com.project.devpulse.dto.GitHubRepoDTO;
import com.project.devpulse.dto.GitHubUserDTO;
import com.project.devpulse.model.DevPulse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DevPulseMapper {

    DevPulseResponseDTO toResponseDTO(DevPulse devPulse);

    DevPulse toEntity(GitHubUserDTO user, List<GitHubRepoDTO> repos);
}