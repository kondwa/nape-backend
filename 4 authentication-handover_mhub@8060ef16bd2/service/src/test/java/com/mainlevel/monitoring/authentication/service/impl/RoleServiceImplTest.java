package com.mainlevel.monitoring.authentication.service.impl;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mainlevel.monitoring.authentication.api.dto.RoleDTO;
import com.mainlevel.monitoring.authentication.repository.RoleRepository;
import com.mainlevel.monitoring.authentication.repository.entity.RoleEntity;
import com.mainlevel.monitoring.authentication.service.RoleService;
import com.mainlevel.monitoring.authentication.service.impl.RoleServiceImpl;
import com.mainlevel.monitoring.common.service.CollectionConversionService;

@SuppressWarnings("javadoc")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RoleServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigContextLoader.class)
public class RoleServiceImplTest {

    public static class TestConfiguration {

        @Bean
        private RoleRepository roleRepository() {
            return mock(RoleRepository.class);
        }

        @Bean
        private CollectionConversionService conversionService() {
            return mock(CollectionConversionService.class);
        }

        @Bean
        private RoleService roleService() {
            return new RoleServiceImpl();
        }

    }

    @Autowired
    RoleService roleService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CollectionConversionService collectionConversionService;

    @After
    public void tearDown() throws Exception {
        reset(roleRepository, collectionConversionService);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetRoles() {
        when(roleRepository.findAll()).thenReturn(singletonList(mock(RoleEntity.class)));
        when(collectionConversionService.convert(anyCollection(), eq(RoleDTO.class))).thenReturn(singletonList(RoleDTO.class));
        final List<? extends RoleDTO> roles = roleService.getRoles();

        assertEquals(1, roles.size());
        verify(roleRepository).findAll();

    }

    @Test
    public void testGetPageableRoles() {
        final Pageable pageable = mock(Pageable.class);
        roleService.getPageableRoles(pageable);
        verify(roleRepository).findAll(pageable);

    }

    @Test
    public void testFindOrCreateRoleWithNonExistingRole() {

        final RoleEntity roleEntityMock = mock(RoleEntity.class);

        when(roleRepository.findByName(anyString())).thenReturn(null);
        when(roleRepository.save(Matchers.<RoleEntity> anyObject())).thenReturn(roleEntityMock);
        final RoleEntity result = roleService.findOrCreateRoleEntity("Superrole");

        verify(roleRepository).findByName(anyString());
        verify(roleRepository).save(Matchers.<RoleEntity> anyObject());

        assertSame(roleEntityMock, result);
    }

    @Test
    public void testFindOrCreateRoleWithExistingRole() {

        final RoleEntity roleEntityMock = mock(RoleEntity.class);

        when(roleRepository.findByName(anyString())).thenReturn(roleEntityMock);

        final RoleEntity result = roleService.findOrCreateRoleEntity("Superrole");

        verify(roleRepository).findByName(anyString());
        verify(roleRepository, times(0)).save(Matchers.<RoleEntity> anyObject());

        assertSame(roleEntityMock, result);
    }

}
