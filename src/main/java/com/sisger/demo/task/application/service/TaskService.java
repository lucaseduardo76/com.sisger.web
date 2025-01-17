package com.sisger.demo.task.application.service;


import com.sisger.demo.company.application.service.CompanyService;
import com.sisger.demo.company.domain.Company;
import com.sisger.demo.company.domain.dto.ResponseCompanyChildDTO;
import com.sisger.demo.exception.BadRequestException;
import com.sisger.demo.exception.NotFoundException;
import com.sisger.demo.exception.UnauthorizedException;
import com.sisger.demo.section.application.service.SectionService;
import com.sisger.demo.section.domain.Section;
import com.sisger.demo.section.domain.dto.ResponseSectionDTO;
import com.sisger.demo.task.domain.StatusRole;
import com.sisger.demo.task.domain.Task;
import com.sisger.demo.task.domain.dto.*;
import com.sisger.demo.task.infra.repository.TaskRepository;
import com.sisger.demo.user.domain.User;
import com.sisger.demo.user.domain.dto.ResponseUserToTaskDTO;
import com.sisger.demo.user.infra.repository.UserRepository;
import com.sisger.demo.util.TextHandler;
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
    private final CompanyService companyService;
    private final UserRepository userRepository;

    @Override
    public Optional<Task> findById(String id) {
        log.info("[inicia] TaskService - findById");
        if(id == null)
            throw new BadRequestException("Id is null");

        log.info("[fim] TaskService - findById");
        return taskRepository.findById(id);
    }

    @Override
    public List<ResponseTaskDTO> findAllTasksBySection(String sectionId, User manager) {
        log.info("[inicia] TaskService - findAllTasksBySection");

        verifyCompanyMatch(sectionService.findById(sectionId).getCompany(), manager.getCompany());

        List<Task> taskList = taskRepository.findAllBySectionId(sectionId);

        if(taskList == null)
            throw new NotFoundException("Tasks not found, verify the section Id");

        handleListOfTasksToSetStatusIfLate(taskList);

        orderTaskList(taskList);

        if(taskRepository.findAllBySectionId(sectionId) == null)
            throw new NotFoundException("Section not found");

        List<ResponseTaskDTO> responseTaskList = taskList.stream()
                .map(task -> ResponseTaskDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .initialDate(task.getInitialDate())
                        .finalDate(task.getFinalDate())
                        .employeeMessage(task.getEmployeeMessage())
                        .status(task.getStatus())
                        .employee(convertUserToResponseUserToTaskDTO(task.getUser()))
                        .build())
                .toList();
        log.info("[fim] TaskService - findAllTasksBySection");

        return responseTaskList;
    }

    @Override
    public List<ResponseTaskFindByUserDTO> findAllTasksByUser(String userId, User manager) {
        log.info("[inicia] TaskService - findAllTasksByUser");
        List<Task> taskList = taskRepository.findAllByUserId(userId);

        if(taskList == null)
            throw new NotFoundException("Tasks not found, verify the user Id");

        handleListOfTasksToSetStatusIfLate(taskList);

        orderTaskList(taskList);

        if(!taskList.isEmpty())
            verifyCompanyMatch(taskList.get(0).getSection().getCompany(), manager.getCompany());

        if(taskRepository.findAllByUserId(userId) == null)
            throw new NotFoundException("Tasks not found, verify the user Id");


        List<ResponseTaskFindByUserDTO> responseTaskList = taskList.stream()
                .map(task -> ResponseTaskFindByUserDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .initialDate(task.getInitialDate())
                        .finalDate(task.getFinalDate())
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
       log.info("[inicia] TaskService - save");
        if(requestTaskDTOTask == null)
            throw new BadRequestException("RequestTaskDTO is null");

        verifyTaskDateOfCreation(requestTaskDTOTask.getInitialDate(), requestTaskDTOTask.getFinalDate());

        Task newTask = taskRepository.save(Task.builder()
                .title(TextHandler.capitalizeWords(requestTaskDTOTask.getTitle()))
                .description(TextHandler.capitalizeFirstLetter(requestTaskDTOTask.getDescription()))
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
    public void delete(String id, User manager) {
        log.info("[inicia] TaskService - delete");

        Task task = this.findById(id).orElseThrow(() -> new NotFoundException("Task not found"));

        verifyCompanyMatch(task.getSection().getCompany(), manager.getCompany());

        if(id == null)
            throw new BadRequestException("Id is null");

        taskRepository.deleteById(id);
        log.info("[fim] TaskService - delete");
    }

    @Override
    public void deleteAllFromSection(String sectionId, User manager) {
        log.info("[inicia] TaskService - deleteAllFromSection");
        verifyCompanyMatch(sectionService.findById(sectionId).getCompany(), manager.getCompany());
        this.taskRepository.deleteAllBySectionId(sectionId);
        log.info("[fim] TaskService - deleteAllFromSection");
    }

    @Override
    public void deleteAllFromUser(User user, User manager) {
        log.info("[inicia] TaskService - deleteAllFromUser");
        if(user == null || manager == null)
            throw new BadRequestException("User is null");

        if(!user.getCompany().equals(manager.getCompany()))
            throw new UnauthorizedException("It is from another company, please verify your request");


        this.taskRepository.deleteAllByUserId(user.getId());
        log.info("[fim] TaskService - deleteAllFromUser");
    }

    @Override
    public void changeStatus(RequestChangeStatusTaskDTO requestChangeStatusTaskDTO, User user) {
        log.info("[inicia] TaskService - changeStatus");

        Task task = this.findById(requestChangeStatusTaskDTO.getTaskId()).orElseThrow(()
                -> new NotFoundException("Task not found"));

        verifyIfUserIsOwnerOfTask(task, user);

        if(task.getStatus().equals(StatusRole.FINISHED))
            throw new BadRequestException("Task is already finished");

        if(requestChangeStatusTaskDTO.getStatus() == null)
            throw new BadRequestException("Status is null");

        if(requestChangeStatusTaskDTO.getStatus().equals(StatusRole.NOT_INITIALIZED)
                || requestChangeStatusTaskDTO.getStatus().equals(StatusRole.LATE))
            throw new BadRequestException("Invalid status");

        if(setStatusIfLate(task) && !requestChangeStatusTaskDTO.getStatus().equals(StatusRole.FINISHED))
            throw new BadRequestException("Task is already late, finish the task as soon as possible");

        task.setStatus(requestChangeStatusTaskDTO.getStatus());
        this.taskRepository.save(task);

        log.info("[fim] TaskService - changeStatus");
    }

    @Override
    public void setEmployeeMessage(RequestEmployeeMessageDTO requestEmployeeMessageDTO, User user) {
        log.info("[inicia] TaskService - setEmployeeMessage");
        Task task = this.findById(requestEmployeeMessageDTO.getTaskId()).orElseThrow(
                () -> new NotFoundException("Task Not Found"));

        verifyIfUserIsOwnerOfTask(task, user);
        task.setEmployeeMessage(requestEmployeeMessageDTO.getMessage());
        taskRepository.save(task);
        log.info("[fim] TaskService - setEmployeeMessage");
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
        ResponseCompanyChildDTO responseCompanyChildDTO = companyService.buildResponseCompanyChildDTOFromCompany(
                section.getCompany());
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
                .initialDate(task.getInitialDate())
                .finalDate(task.getFinalDate())
                .employeeMessage(task.getEmployeeMessage())
                .status(task.getStatus())
                .employee(convertUserToResponseUserToTaskDTO(task.getUser()))
                .build();

    }

    private void verifyCompanyMatch(Company company1, Company company2){
        if(!company1.getId().equals(company2.getId()))
            throw new UnauthorizedException("It is from another company, please verify your request");
    }

    private void verifyTaskDateOfCreation(LocalDate initialDate, LocalDate finalDate){
        log.info("[inicia] TaskService - verifyTaskDateOfCreation");

        if(initialDate == null || finalDate == null)
            throw new BadRequestException("Initial date or final date is null");

        if(initialDate.isBefore(LocalDate.now()))
            throw new BadRequestException("Initial date cannot be before today");

        if(finalDate.isBefore(initialDate))
            throw new BadRequestException("Final date cannot be before initial date");
        log.info("[fim] TaskService - verifyTaskDateOfCreation");
    }

    private void handleListOfTasksToSetStatusIfLate(List<Task> taskList){
        log.info("[inicia] TaskService - handleListOfTasksToSetStatusIfLate");
        taskList.forEach(this::setStatusIfLate);
        log.info("[fim] TaskService - handleListOfTasksToSetStatusIfLate");
    }

    private boolean setStatusIfLate(Task task){
        log.info("[inicia] TaskService - setStatusIfLate");
        if(task.getFinalDate().isBefore(LocalDate.now())
                && !task.getStatus().equals(StatusRole.PAUSED)
                && !task.getStatus().equals(StatusRole.FINISHED)){
            task.setStatus(StatusRole.LATE);
            this.taskRepository.save(task);
            log.info("[fim] TaskService - setStatusIfLate");
            return true;
        }

        log.info("[fim] TaskService - setStatusIfLate");
        return false;
    }

    private void orderTaskList(List<Task> taskList){
        log.info("[inicia] TaskService - orderByStatus");
        taskList.sort((task1, task2) -> task1.getInitialDate().compareTo(task2.getInitialDate()));

        taskList.sort((task1, task2) -> task1.getStatus().compareTo(task2.getStatus()));

        log.info("[fim] TaskService - orderByStatus");
    }

    private void verifyIfUserIsOwnerOfTask(Task task, User user){
        log.info("[inicia] TaskService - verifyIfUserIsOwnerOfTask");
        if(!task.getUser().getId().equals(user.getId()))
            throw new BadRequestException("User is not the owner of the task, please verify your request");
        log.info("[fim] TaskService - verifyIfUserIsOwnerOfTask");
    }

}
