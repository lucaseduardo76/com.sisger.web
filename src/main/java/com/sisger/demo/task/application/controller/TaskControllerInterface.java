package com.sisger.demo.task.application.controller;


import com.sisger.demo.task.domain.dto.RequestChangeStatusTaskDTO;
import com.sisger.demo.task.domain.dto.RequestTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskFindByUserDTO;
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
                                               @RequestBody RequestTaskDTO requestTaskDTOTask);
    @DeleteMapping("/delete/{id}")
    ResponseEntity<HttpStatus> delete(@RequestHeader(name = "Authorization", required = true) String token,
                                      @PathVariable String id);

    @PostMapping("/change-status")
    ResponseEntity<List<ResponseTaskDTO>> changeStatus(
            @RequestHeader(name = "Authorization", required = true) String token,
            @RequestBody RequestChangeStatusTaskDTO requestChangeStatusTaskDTO);
}
