/*
 * Copyright (c) 2017 PRODYNA AG. All rights reserved.
 */

package com.mainlevel.monitoring.mail.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mainlevel.monitoring.mail.api.dto.MailAddress;
import com.mainlevel.monitoring.mail.api.dto.MailBodyDTO;
import com.mainlevel.monitoring.mail.api.dto.MailDTO;
import com.mainlevel.monitoring.mail.api.dto.MailHeaderDTO;
import com.mainlevel.monitoring.mail.api.dto.TemplateMailBodyDTO;
import com.mainlevel.monitoring.mail.api.dto.TextMailBodyDTO;
import com.mainlevel.monitoring.mail.api.exception.MailTemplateException;
import com.mainlevel.monitoring.mail.api.exception.SendMailException;
import com.mainlevel.monitoring.mail.service.MailService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Default implementation of {@link MailService}.
 */
@Service
public class MailServiceImpl implements MailService {

    private static final String TEMPLATE_SUFFIX = ".ftl";

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration templateConfig;

    @Async
    @Override
    public void sendMail(MailDTO mail) {

        MailHeaderDTO header = mail.getHeader();
        MailBodyDTO body = mail.getBody();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setSubject(header.getSubject());

            helper.setTo(convertAddresses(header.getToAdresses()));
            helper.setCc(convertAddresses(header.getCcAdresses()));
            helper.setBcc(convertAddresses(header.getBccAdresses()));

            helper.setFrom(convertAddress(header.getFrom()));
            helper.setReplyTo(convertAddress(header.getReplyTo()));

            helper.setSentDate(new Date());

            switch (body.getType()) {

                case TEXT: {
                    TextMailBodyDTO textBody = (TextMailBodyDTO) body;
                    helper.setText(textBody.getText(), textBody.getHtml());
                    break;
                }

                case TEMPLATE: {
                    TemplateMailBodyDTO templateBody = (TemplateMailBodyDTO) body;

                    String templateName = templateBody.getTemplateName() + TEMPLATE_SUFFIX;
                    try {
                        Template template = templateConfig.getTemplate(templateName);
                        String templateText = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateBody.getParameters());
                        helper.setText(templateText, true);

                        List<Resource> attachments = loadTemplateAttachments(templateName);
                        for (Resource attachment : attachments) {
                            helper.addAttachment(attachment.getFilename(), attachment.getFile());
                        }

                    } catch (IOException | TemplateException e) {
                        throw new MailTemplateException(templateName);
                    }
                    break;
                }
            }

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new SendMailException(header.getSubject(),
                header.getToAdresses().stream().map(to -> to.getAddress()).collect(Collectors.joining(",")));
        }

    }

    /**
     * Load attachments of a given template.
     *
     * @param templateName name of the template
     * @return the list of resources for this template
     */
    private List<Resource> loadTemplateAttachments(String templateName) {
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
            Resource[] resources = resolver.getResources("classpath*:templates/" + templateName + "/*");
            return Arrays.asList(resources);
        } catch (IOException e) {
            throw new RuntimeException("Error loading template attachments.", e);
        }
    }

    /**
     * Convert the list of addresses to a plain string array.
     *
     * @param addresses
     * @return the converted addresses
     */
    private InternetAddress[] convertAddresses(List<MailAddress> addresses) {

        List<InternetAddress> plainAddresses = new ArrayList<>();

        if (addresses != null) {
            for (MailAddress address : addresses) {
                InternetAddress internetAddress = convertAddress(address);
                plainAddresses.add(internetAddress);
            }
        }

        return plainAddresses.toArray(new InternetAddress[plainAddresses.size()]);
    }

    /**
     * Converts the given address to an internet address.
     *
     * @param address the address to convert
     * @return the converted address
     */
    private InternetAddress convertAddress(MailAddress address) {
        try {
            InternetAddress internetAddress = new InternetAddress(address.getAddress(), address.getName());
            return internetAddress;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Mail encoding is not supported.", e);
        }
    }

}
