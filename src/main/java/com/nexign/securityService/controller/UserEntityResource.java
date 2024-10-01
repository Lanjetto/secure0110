package com.nexign.securityService.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nexign.securityService.entity.UserEntity;
import com.nexign.securityService.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/rest/admin-ui/userEntities")
public class UserEntityResource {

    private final UserEntityRepository userEntityRepository;

    private final ObjectMapper objectMapper;


    @Autowired
    public UserEntityResource(UserEntityRepository userEntityRepository,
                              ObjectMapper objectMapper) {
        this.userEntityRepository = userEntityRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<Collection<UserEntity>> getList() {
        return ResponseEntity.status(HttpStatus.FOUND).body(userEntityRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserEntity getOne(@PathVariable(name = "id") Long id) {
        Optional<UserEntity> userEntityOptional = userEntityRepository.findById(id);
        return userEntityOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    @GetMapping("/by-ids")
    public List<UserEntity> getMany(@RequestParam List<Long> ids) {
        return userEntityRepository.findAllById(ids);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody UserEntity userEntity) {
        String pass = userEntity.getPass();
        userEntityRepository.save(userEntity);
        return Collections.singletonMap("user", userEntity.getLogin());
    }

    @PatchMapping("/{id}")
    public UserEntity patch(@PathVariable(name = "id") Long id, @RequestBody JsonNode patchNode) throws IOException {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(userEntity).readValue(patchNode);

        return userEntityRepository.save(userEntity);
    }


    @PatchMapping
    public List<Long> patchMany(@RequestParam List<Long> ids, @RequestBody JsonNode patchNode) throws IOException {
        Collection<UserEntity> userEntities = userEntityRepository.findAllById(ids);

        for (UserEntity userEntity : userEntities) {
            objectMapper.readerForUpdating(userEntity).readValue(patchNode);
        }

        List<UserEntity> resultUserEntities = userEntityRepository.saveAll(userEntities);
        return resultUserEntities.stream()
                .map(UserEntity::getId)
                .toList();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> delete(@PathVariable(name = "id") Long id) {
        UserEntity userEntity = userEntityRepository.findById(id).orElse(null);
        if (userEntity != null) {
            userEntityRepository.delete(userEntity);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userEntity);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<Long> ids) {
        userEntityRepository.deleteAllById(ids);
    }
}
