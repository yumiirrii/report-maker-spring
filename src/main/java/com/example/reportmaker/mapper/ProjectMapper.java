package com.example.reportmaker.mapper;

import com.example.reportmaker.domain.Project;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectMapper {

    void insertProject(Project project);

    Project selectProjectByName(String name);

    void deleteProjectByProjectId(Integer projectId);
}
