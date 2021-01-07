package com.mainlevel.monitoring.admin.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainlevel.monitoring.admin.repository.IndicatorRepository;
import com.mainlevel.monitoring.admin.repository.entity.IndicatorGroupEntity;
import com.mainlevel.monitoring.admin.repository.entity.UserEntity;
import com.mainlevel.monitoring.admin.service.IndicatorService;
import com.mainlevel.monitoring.admin.service.UnitService;
import com.mainlevel.monitoring.admin.service.UserService;
import com.mainlevel.monitoring.admin.service.util.IndicatorGroupPermissionVisitor;
import com.mainlevel.monitoring.common.service.AuthenticationAccessService;

/**
 * Default implementation of {@link IndicatorService}.
 */
@Service
public class IndicatorServiceImpl implements IndicatorService {

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private UnitService unitService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationAccessService authenticationAccessService;

    @Override
    public Set<IndicatorGroupEntity> loadGroups() {

        List<IndicatorGroupEntity> groups = indicatorRepository.findAll();

        String username = authenticationAccessService.getCurrentUsername();
        UserEntity user = userService.findByUserName(username);

        IndicatorGroupPermissionVisitor visitor = new IndicatorGroupPermissionVisitor(groups);
        user.getParticipations().stream().filter(p -> p.getUnit() != null).forEach(p -> unitService.loadDeep(p.getUnit()).accept(visitor));

        return visitor.getPermittedGroups();
    }

    @Override
    public IndicatorGroupEntity save(IndicatorGroupEntity group) {
        return indicatorRepository.save(group);
    }

    @Override
    public IndicatorGroupEntity loadIndicatorsGroup(String groupId) {
        return indicatorRepository.findOne(groupId);
    }

}
