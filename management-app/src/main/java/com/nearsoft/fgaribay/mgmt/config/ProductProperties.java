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
    RoutingKeys routingKeys;

    @NotNull
    private RoutingKeys queues;

    @NotNull
    private Exchanges exchanges;

    public RoutingKeys getRoutingKeys() {
        return routingKeys;
    }

    public void setRoutingKeys(RoutingKeys routingKeys) {
        this.routingKeys = routingKeys;
    }

    public Exchanges getExchanges() {
        return exchanges;
    }

    public void setExchanges(Exchanges exchanges) {
        this.exchanges = exchanges;
    }

    public RoutingKeys getQueues() {
        return queues;
    }

    public void setQueues(RoutingKeys queues) {
        this.queues = queues;
    }

    public static class Queues {

        @NotNull
        private String creationName;

        @NotNull
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

    public static class Exchanges {

        @NotNull
        private String creationName;

        @NotNull
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

    public static class RoutingKeys {

        @NotNull
        private String creationName;

        @NotNull
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
