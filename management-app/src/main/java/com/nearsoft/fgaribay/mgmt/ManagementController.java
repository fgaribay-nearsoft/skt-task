package com.nearsoft.fgaribay.mgmt;

import com.nearsoft.fgaribay.mgmt.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/products")
public class ManagementController {

    private BrokerConnector brokerConnector;

    @Autowired
    public ManagementController(BrokerConnector brokerConnector) {
        this.brokerConnector = brokerConnector;
    }

    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String viewPersonList(Model model) {
        model.addAttribute("products", brokerConnector.retrieveProductList());
        return "listProducts";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String greetingForm(Model model) {
        model.addAttribute("product", new Product());
        return "createProduct";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("product") Product product) {
        brokerConnector.createProduct(product);
        return "listProducts";
    }
}
