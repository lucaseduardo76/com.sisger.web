package com.sisger.demo.task.application.controller;

import com.sisger.demo.infra.security.TokenService;
import com.sisger.demo.task.application.service.TaskService;
import com.sisger.demo.task.domain.dto.RequestChangeStatusTaskDTO;
import com.sisger.demo.task.domain.dto.RequestTaskDTO;
import com.sisger.demo.task.domain.dto.ResponseTaskDTO;
import com.sisger.demo.user.application.service.UserService;
import com.sisger.demo.util.AuthorityChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Log4j2
public class TaskController implements TaskControllerInterface {

    private final TaskService taskService;
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public ResponseEntity<List<ResponseTaskDTO>> findAllTasksBySection(String token, String sectionId) {
        log.info("[inicia] TaskController - findAllTasksBySection");
        var user = tokenService.getUserByToken(token);
        AuthorityChecker.requireManagerAuthority(user);

        log.info("[fim] TaskController - findAllTasksBySection");
        return ResponseEntity.ok().body(taskService.findAllTasksBySection(sectionId));
    }

    @Override
    public ResponseEntity<List<ResponseTaskDTO>> findAllTasksByUser(String token, String userId) {
        log.info("[inicia] TaskController - findAllTasksByUser");
        var user = tokenService.getUserByToken(token);
        AuthorityChecker.requireManagerAuthority(user);

        log.info("[fim] TaskController - findAllTasksByUser");
        return ResponseEntity.ok().body(taskService.findAllTasksByUser(userId));
    }

    @Override
    public ResponseEntity<ResponseTaskDTO> save(String token, RequestTaskDTO requestTaskDTOTask) {
        log.info("[inicia] TaskController - save");
        var user = tokenService.getUserByToken(token);
        AuthorityChecker.requireManagerAuthority(user);

        var task = taskService.save(requestTaskDTOTask,
                userService.findById(requestTaskDTOTask.getUserId()));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        log.info("[fim] TaskController - save");
        return ResponseEntity.created(uri).body(task);
    }

    @Override
    public ResponseEntity<HttpStatus> delete(String token, String id) {
        log.info("[inicia] TaskController - delete");

        log.info("[fim] TaskController - delete");
        return null;
    }

    @Override
    public ResponseEntity<List<ResponseTaskDTO>> changeStatus(
            String token, RequestChangeStatusTaskDTO requestChangeStatusTaskDTO) {
        log.info("[inicia] TaskController - changeStatus");

        log.info("[fim] TaskController - changeStatus");
        return null;
    }


}
