package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/products")
public class ManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagementController.class);

    private BrokerConnector brokerConnector;

    public ManagementController(BrokerConnector brokerConnector) {
        this.brokerConnector = brokerConnector;
    }

    @GetMapping(value = {"/", "/list"})
    public String viewPersonList(Model model) {
        LOGGER.debug("Received GET request for the product list.");

        model.addAttribute("products", brokerConnector.retrieveProductList());
        return "listProducts";
    }

    @GetMapping(value = "/create")
    public String greetingForm(Model model) {
        LOGGER.debug("Received GET request for the product creation form.");

        model.addAttribute("product", new Product());
        return "createProduct";
    }

    @PostMapping(value = "/create")
    public String submit(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, Model model) {
        LOGGER.debug("Received POST request for the product creation form.");

        if (bindingResult.hasErrors()) {
            LOGGER.error("A POST request for creating a product had invalid data.");
            return "createProduct";
        }

        brokerConnector.createProduct(product);
        return viewPersonList(model);
    }
}
