package com.mainlevel.monitoring.admin.resource;

import static com.mainlevel.monitoring.common.constant.RoleConstant.ROLE_USER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainlevel.monitoring.admin.api.dto.unit.UnitDTO;
import com.mainlevel.monitoring.admin.api.dto.unit.UnitListDTO;
import com.mainlevel.monitoring.admin.api.dto.user.UserDTO;
import com.mainlevel.monitoring.admin.api.dto.user.UserListDTO;
import com.mainlevel.monitoring.admin.api.resource.UnitResource;
import com.mainlevel.monitoring.admin.repository.entity.UnitEntity;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.admin.repository.entity.UserParticipationEntity;
import com.mainlevel.monitoring.admin.service.UnitService;
import com.mainlevel.monitoring.admin.service.UserService;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

/**
 * Default implementation of {@link UnitResource}.
 */
@RestController
@Secured(ROLE_USER)
public class UnitResourceImpl implements UnitResource {

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private CollectionConversionService collectionConversionService;

    @Override
    public ResponseEntity<UnitListDTO> getUnits(@RequestParam("type") String type) {
        List<UnitEntity> units = unitService.findUnitsByType(type);

        List<UnitDTO> dtos = collectionConversionService.convert(units, UnitDTO.class);

        UnitListDTO listDto = UnitListDTO.builder().units(dtos).build();
        return ResponseEntity.ok(listDto);
    }

    @Override
    public ResponseEntity<UnitDTO> getRoot() {

        String username = authenticationAccessService.getCurrentUsername();
        UnitEntity unit = unitService.findDeepForUser(username);

        UnitDTO dto = conversionService.convert(unit, UnitDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UnitDTO> getUnitById(@RequestParam(name = "id", required = true) String id) {

        UnitEntity unit = unitService.findUnitById(id);

        UnitDTO dto = conversionService.convert(unit, UnitDTO.class);
        return ResponseEntity.ok(dto);

    }

    @Override
    public ResponseEntity<UnitDTO> getUnit(@PathVariable("foreignId") String foreignId) {

        UnitEntity unit = unitService.findUnitByForeignId(foreignId);

        UnitDTO dto = conversionService.convert(unit, UnitDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UnitDTO> create(@RequestBody UnitDTO unit) {
        UnitEntity entity = conversionService.convert(unit, UnitEntity.class);
        entity = unitService.saveByForeignId(entity);

        UnitDTO dto = conversionService.convert(entity, UnitDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UnitDTO> update(@PathVariable("foreignId") String key, @RequestBody UnitDTO unit) {
        UnitEntity entity = conversionService.convert(unit, UnitEntity.class);
        entity = unitService.saveByForeignId(entity);

        UnitDTO dto = conversionService.convert(entity, UnitDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UnitDTO> delete(@PathVariable("foreignId") String key) {

        UnitEntity entity = unitService.deleteByForeignId(key);

        UnitDTO dto = conversionService.convert(entity, UnitDTO.class);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<UserListDTO> setUsersForUnit(@RequestBody UserListDTO body, @PathVariable("foreignId") String key) {

        UnitEntity unit = unitService.findUnitByForeignId(key);

        if (unit == null) {
            throw new IllegalArgumentException("Unit with key " + key + " does not exist.");
        }
        List<UserDTO> users = new ArrayList<>();

        body.getUsers().forEach(user -> {

            UserEntity entity = userService.findByUserName(user.getUserName());

            if (entity == null) {
                entity = UserEntity.builder().name(user.getName()).userName(user.getUserName()).participations(new ArrayList<>())
                    .dashboards(Arrays.asList("DEFAULT")).build();
            }

            if (!entity.getParticipations().stream().filter(p -> key.equals(p.getUnit().getForeignId())).findAny().isPresent()) {
                UserParticipationEntity participation = UserParticipationEntity.builder().unit(unit).role("ASSISTANT").build();
                entity.getParticipations().add(participation);
            }

            entity = userService.save(entity);

            users.add(conversionService.convert(user, UserDTO.class));
        });

        UserListDTO dto = UserListDTO.builder().users(users).build();

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Void> deleteUserParticipation(@PathVariable("foreignId") String key, @PathVariable("username") String username) {

        UserEntity user = userService.findByUserName(username);

        List<UserParticipationEntity> orphanedParticipations =
            user.getParticipations().stream().filter(p -> key.equals(p.getUnit().getForeignId())).collect(Collectors.toList());

        user.getParticipations().removeAll(orphanedParticipations);

        userService.save(user);

        return ResponseEntity.ok().build();
    }
}
