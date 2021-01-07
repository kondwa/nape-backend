package com.mainlevel.monitoring.admin.converter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mainlevel.monitoring.admin.api.dto.template.PageDTO;
import com.mainlevel.monitoring.admin.api.dto.template.QuestionDTO;
import com.mainlevel.monitoring.admin.repository.entity.PageEntity;
import com.mainlevel.monitoring.common.converter.AbstractApplicationAwareConverter;

/**
 * PageDTO to PageEntity converter.
 */
@Component
public class PageEntityToPageDTOConverter extends AbstractApplicationAwareConverter<PageEntity, PageDTO> {

    @Override
    public PageDTO convert(PageEntity page) {
        final List<QuestionDTO> questions = getCollectionConversionService().convert(page.getQuestions(), QuestionDTO.class);

        final PageDTO target = PageDTO.builder().name(page.getName()).title(page.getTitle()).visible(page.getVisible())
            .description(page.getDescription()).questions(questions).build();

        return target;
    }
}
