package com.sisger.demo.task.application.service;


import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.exception.BadRequestException;
import com.sisger.demo.exception.NotFoundException;
import com.sisger.demo.section.application.service.SectionService;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import com.sisger.demo.task.domain.StatusRole;
import com.sisger.demo.task.domain.Task;
import com.sisger.demo.task.domain.dto.RequestChangeStatusTaskDTO;
import com.sisger.demo.task.domain.dto.RequestTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskFindByUserDTO;
import com.sisger.demo.task.infra.repository.TaskRepository;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.ResponseUserToTaskDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskService implements TaskServiceInterface{

    private final TaskRepository taskRepository;
    private final SectionService sectionService;

    @Override
    public List<ResponseTaskDTO> findAllTasksBySection(String sectionId) {
        log.info("[inicia] TaskService - findAllTasksBySection");
        List<Task> taskList = taskRepository.findAllBySectionId(sectionId);

        if(taskRepository.findAllBySectionId(sectionId) == null)
            throw new NotFoundException("Section not found");

        List<ResponseTaskDTO> responseTaskList = taskList.stream()
                .map(task -> ResponseTaskDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .initialDate(handleFormatdDate(task.getInitialDate()))
                        .finalDate(handleFormatdDate(task.getFinalDate()))
                        .employeeMessage(task.getEmployeeMessage())
                        .status(task.getStatus())
                        .employee(convertUserToResponseUserToTaskDTO(task.getUser()))
                        .build())
                .toList();
        log.info("[fim] TaskService - findAllTasksBySection");

        return responseTaskList;
    }

    @Override
    public List<ResponseTaskFindByUserDTO> findAllTasksByUser(String userId) {
        log.info("[inicia] TaskService - findAllTasksByUser");
        List<Task> taskList = taskRepository.findAllByUserId(userId);
        if(taskRepository.findAllByUserId(userId) == null)
            throw new NotFoundException("Tasks not found, verify the user Id");

        List<ResponseTaskFindByUserDTO> responseTaskList = taskList.stream()
                .map(task -> ResponseTaskFindByUserDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .initialDate(handleFormatdDate(task.getInitialDate()))
                        .finalDate(handleFormatdDate(task.getFinalDate()))
                        .employeeMessage(task.getEmployeeMessage())
                        .status(task.getStatus())
                        .section(convertSectionToResponseDTO(task.getSection()))
                        .build())
                .toList();

        log.info("[fim] TaskService - findAllTasksByUser");
        return responseTaskList;
    }

    @Override
    public ResponseTaskDTO save(RequestTaskDTO requestTaskDTOTask, User employee) {
        if(requestTaskDTOTask == null)
            throw new BadRequestException("RequestTaskDTO is null");

        Task newTask = taskRepository.save(Task.builder()
                .title(requestTaskDTOTask.getTitle())
                .description(requestTaskDTOTask.getDescription())
                .initialDate(requestTaskDTOTask.getInitialDate())
                .finalDate(requestTaskDTOTask.getFinalDate())
                .status(StatusRole.NOT_INITIALIZED)
                .section(sectionService.findById(requestTaskDTOTask.getSectionId()))
                .user(employee)
                .build());
        log.info("[fim] TaskService - save");


        return buildTaskResponse(newTask);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void deleteAllFromSection(String sectionId) {

    }

    @Override
    public void deleteAllFromUser(String userId) {

    }

    @Override
    public Optional<ResponseTaskDTO> changeStatus(RequestChangeStatusTaskDTO requestChangeStatusTaskDTO) {
        return Optional.empty();
    }

    private LocalDate handleFormatdDate(LocalDate date){
        log.info("[inicia] TaskService - handleFormatdDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        log.info("[fim] TaskService - handleFormatdDate");
        return LocalDate.parse(date.format(formatter),formatter);
    }

    private ResponseUserToTaskDTO convertUserToResponseUserToTaskDTO(User user){
        log.info("[inicia] TaskService - convertUserToResponseUserToTaskDTO");

        var responseUserToTaskDTO = ResponseUserToTaskDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();

        log.info("[fim] TaskService - convertUserToResponseUserToTaskDTO");
        return responseUserToTaskDTO;
    }

    private ResponseSectionDTO convertSectionToResponseDTO(Section section){
        log.info("[inicia] TaskService - convertSectionToResponseDTO");
        ResponseCompanyChildDTO responseCompanyChildDTO = ResponseCompanyChildDTO.builder()
                .id(section.getCompany().getId())
                .name(section.getCompany().getName())
                .build();
        log.info("[fim] TaskService - convertSectionToResponseDTO");
        return ResponseSectionDTO.builder()
                .id(section.getId())
                .name(section.getName())
                .company(responseCompanyChildDTO)
                .build();
    }
    private ResponseTaskDTO buildTaskResponse(Task task){

        return  ResponseTaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .initialDate(handleFormatdDate(task.getInitialDate()))
                .finalDate(handleFormatdDate(task.getFinalDate()))
                .employeeMessage(task.getEmployeeMessage())
                .status(task.getStatus())
                .employee(convertUserToResponseUserToTaskDTO(task.getUser()))
                .build();

    }

}
