package com.mainlevel.monitoring.common.configuration.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.mainlevel.monitoring.common.configuration.CloudConfiguration;

/**
 * Annotation to enable the cloud configuration.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(CloudConfiguration.class)
public @interface EnableCloudServices {

}
