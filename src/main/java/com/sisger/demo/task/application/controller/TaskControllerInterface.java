package com.sisger.demo.task.application.controller;


import com.sisger.demo.task.domain.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/task")
public interface TaskControllerInterface {

    @GetMapping("/find-by-section/{sectionId}")
    ResponseEntity<List<ResponseTaskDTO>> findAllTasksBySection(
            @RequestHeader(name = "Authorization", required = true) String token, @PathVariable String sectionId);

    @GetMapping("/find-by-user/{userId}")
    ResponseEntity<List<ResponseTaskFindByUserDTO>> findAllTasksByUser(
            @RequestHeader(name = "Authorization", required = true) String token, @PathVariable String userId);

    @PostMapping("/create")
    ResponseEntity<ResponseTaskDTO> save(@RequestHeader(name = "Authorization", required = true) String token,
                                               @RequestBody @Valid RequestTaskDTO requestTaskDTOTask);
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@RequestHeader(name = "Authorization", required = true) String token,
                                      @RequestParam String id);

    @PatchMapping("/change-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void changeStatus(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody @Valid RequestChangeStatusTaskDTO requestChangeStatusTaskDTO);

    @PatchMapping("/employee-messagee")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void setEmployeMessage(@RequestHeader(name = "Authorization", required = true) String token,
                           @RequestBody @Valid RequestEmployeeMessageDTO requestEmployeeMessageDTO);
}
