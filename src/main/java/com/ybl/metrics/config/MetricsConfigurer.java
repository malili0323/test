package com.ybl.metrics.config;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yangbolin on 2017/3/28.
 */
@Configuration
@EnableMetrics
public class MetricsConfigurer extends MetricsConfigurerAdapter {

    /**
     * 配置Reporters,可以配置多个
     *
     * @param metricRegistry
     */
    @Override
    public void configureReporters(MetricRegistry metricRegistry) {
        // registerReporter allows the MetricsConfigurerAdapter to
        // shut down the reporter when the Spring context is closed
        registerReporter(
            ConsoleReporter.forRegistry(metricRegistry).build()
        ).start(1, TimeUnit.MINUTES);

        registerReporter(
            Slf4jReporter.forRegistry(metricRegistry).build()
        ).start(1, TimeUnit.MINUTES);
    }

}
