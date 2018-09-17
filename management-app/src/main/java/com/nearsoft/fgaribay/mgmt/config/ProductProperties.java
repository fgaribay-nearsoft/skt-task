package com.nearsoft.fgaribay.mgmt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Configuration
@ConfigurationProperties(prefix = "product")
public class ProductProperties {

    @NotNull
    private Queues queues;

    public Queues getQueues() {
        return queues;
    }

    public void setQueues(Queues queues) {
        this.queues = queues;
    }

    public static class Queues {
        private String creationName;
        private String listName;

        public String getCreationName() {
            return creationName;
        }

        public void setCreationName(String creationName) {
            this.creationName = creationName;
        }

        public String getListName() {
            return listName;
        }

        public void setListName(String listName) {
            this.listName = listName;
        }
    }

}
