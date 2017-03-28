package com.ybl.metrics.controller;

import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsResource {

    @Autowired
    private MetricRegistry registry;

    public MetricsResource() {
    }

    @RequestMapping("")
    public MetricRegistry getMetrics() {
        return registry;
    }

}
