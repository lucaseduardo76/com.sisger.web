package com.sisger.demo.task.application.service;

import com.sisger.demo.task.domain.Task;
import com.sisger.demo.task.domain.dto.RequestChangeStatusTaskDTO;
import com.sisger.demo.task.domain.dto.RequestTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskFindByUserDTO;
import com.sisger.demo.user.domain.User;


import java.util.List;
import java.util.Optional;

public interface TaskServiceInterface {

    List<ResponseTaskDTO> findAllTasksBySection(String sectionId);

    List<ResponseTaskFindByUserDTO> findAllTasksByUser(String sectionId);

    ResponseTaskDTO save(RequestTaskDTO requestTaskDTOTask, User employee);

    void delete(String id);

    void deleteAllFromSection(String sectionId);

    void deleteAllFromUser(String userId);

    Optional<ResponseTaskDTO> changeStatus(RequestChangeStatusTaskDTO requestChangeStatusTaskDTO);
}
