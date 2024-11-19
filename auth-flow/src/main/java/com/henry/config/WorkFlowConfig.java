package com.henry.config;

import com.henry.connector.DebeziumConnector;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowConfig implements CommandLineRunner {

    private final DebeziumConnector debeziumConnector;

    public WorkFlowConfig(DebeziumConnector debeziumConnector) {
        this.debeziumConnector = debeziumConnector;
    }

    @Override
    public void run(String... args) {
        createDebeziumConnector();
    }

    private void createDebeziumConnector() {
        debeziumConnector.sendConnector("auth-workflow-connector", "auth-workflow-connector-config.json");
    }
}
