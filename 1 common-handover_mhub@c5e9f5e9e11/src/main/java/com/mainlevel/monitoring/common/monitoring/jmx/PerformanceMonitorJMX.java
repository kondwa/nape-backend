package com.mainlevel.monitoring.common.monitoring.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * JMX bean for storing performance information.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = false)
@ManagedResource
@Component
public class PerformanceMonitorJMX {

    private String methodName;
    private long duration;

    /**
     * Getter for the method name.
     *
     * @return name of the message
     */
    @ManagedAttribute(description = "name of the monitored method")
    public String getMethodName() {
        return methodName;
    }

    /**
     * Getter for the invocation duration.
     *
     * @return the duration in ms as long
     */
    @ManagedAttribute(description = "duration in ms")
    public long getDuration() {
        return duration;
    }
}
