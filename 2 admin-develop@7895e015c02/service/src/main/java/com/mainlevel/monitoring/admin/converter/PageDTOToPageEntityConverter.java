package com.mainlevel.monitoring.admin.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.PageDTO;
import com.mainlevel.monitoring.admin.repository.entity.PageEntity;
import com.mainlevel.monitoring.admin.repository.entity.QuestionEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * PageDTO to PageEntity converter.
 */
@Component
public class PageDTOToPageEntityConverter extends AbstractApplicationAwareConverter<PageDTO, PageEntity> {

    @Override
    public PageEntity convert(PageDTO page) {
        final List<QuestionEntity> questionList = getCollectionConversionService().convert(page.getQuestions(), QuestionEntity.class);

        final PageEntity target = PageEntity.builder().name(page.getName()).title(page.getTitle()).visible(page.getVisible())
            .description(page.getDescription()).questions(questionList).build();

        return target;
    }
}
