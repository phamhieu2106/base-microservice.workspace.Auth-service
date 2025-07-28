package com.base.config;

import com.base.common.annotation.BaseWorkflowConnector;
import org.springframework.context.annotation.Configuration;

@Configuration
@BaseWorkflowConnector(connectorName = "base-workflow-auth-connector", schema = "auth-event")
public class WorkFlowConfig {

}
