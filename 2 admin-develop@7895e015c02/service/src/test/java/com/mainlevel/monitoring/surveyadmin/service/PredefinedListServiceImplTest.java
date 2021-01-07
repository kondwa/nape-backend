package com.mainlevel.monitoring.surveyadmin.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.mainlevel.monitoring.admin.repository.PredefinedListItemRepository;
import com.mainlevel.monitoring.admin.repository.entity.PredefinedListItemEntity;
import com.mainlevel.monitoring.admin.service.impl.PredefinedListServiceImpl;
import com.mainlevel.monitoring.common.constant.ProfileConstant;

@SuppressWarnings("javadoc")
@ActiveProfiles(ProfileConstant.WITHOUT_SECURITY)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PredefinedListServiceImplTest.TestConfiguration.class}, loader = AnnotationConfigWebContextLoader.class)
public class PredefinedListServiceImplTest {

    public static class TestConfiguration {

        @Bean
        public PredefinedListServiceImpl service() {
            return new PredefinedListServiceImpl();
        }

        @Bean
        public PredefinedListItemRepository repository() {
            return mock(PredefinedListItemRepository.class);
        }
    }

    @Autowired
    private PredefinedListServiceImpl service;

    @Autowired
    private PredefinedListItemRepository repository;

    @After
    public void tearDown() {
    }

    @Test
    public void getAvailableLists() {

        PredefinedListItemEntity country = PredefinedListItemEntity.builder().type("COUNTRY").build();
        PredefinedListItemEntity region = PredefinedListItemEntity.builder().type("REGIONS").build();

        when(repository.findAllTypes()).thenReturn(new HashSet<PredefinedListItemEntity>(Arrays.asList(country, region)));

        Set<String> result = service.getAvailableLists();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("COUNTRY", result.stream().findFirst().get());
    }

    @Test
    public void getItems() {

        PredefinedListItemEntity item = PredefinedListItemEntity.builder().code("123").build();
        when(repository.findByType("COUNTRY")).thenReturn(Arrays.asList(item));

        List<PredefinedListItemEntity> items = service.getItems("Country");

        assertNotNull(items);
        assertEquals(1, items.size());
        assertEquals("123", items.get(0).getCode());
    }

    @Test
    public void saveByCode() {

        PredefinedListItemEntity item = PredefinedListItemEntity.builder().code("123").type("COUNTRY").build();
        PredefinedListItemEntity dbItem = PredefinedListItemEntity.builder().code("555").build();

        when(repository.findByCodeAndType("123", "COUNTRY")).thenReturn(dbItem);
        when(repository.save(dbItem)).thenReturn(dbItem);

        PredefinedListItemEntity savedItem = service.saveByCode(item);

        assertNotNull(savedItem);
        assertEquals("555", savedItem.getCode());
    }

}