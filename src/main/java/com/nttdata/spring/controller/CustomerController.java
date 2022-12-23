package com.nttdata.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.spring.persistence.Customer;
import com.nttdata.spring.services.CustomerManagementImpl;

@RestController
@RequestMapping("/index")
public class CustomerController {

	@Autowired
	CustomerManagementImpl customerService;
	static final String CUSTOMERS = "customers";
	static final String LIST_OF_CUSTOMERS = "listOfCustomers";

	/**
	 * Metodo para registrar un cliente
	 * 
	 * @param customer
	 * @param bRes
	 */
	@PostMapping("/registerNewCustomer")
	public void registerCustomer(@ModelAttribute("customer") Customer customer, BindingResult bRes) {
		customerService.addCustomer(customer);
	}

	/**
	 * Metodo para mostrar todos los clientes
	 * 
	 * @return
	 */
	@GetMapping("/showCustomers")
	public List<Customer> listOfCustomers() {
		return customerService.findAll();
	}

	/**
	 * Metodo para buscar por nombre
	 * 
	 * @param customerName
	 * @param model
	 * @return
	 */
	@PostMapping("/searchCustomerByName")
	public List<Customer> searchCustomerByName(@RequestParam String customerName) {
		return customerService.findByCustomerName(customerName);
	}

	/**
	 * Metodo para buscar por nombre completo
	 * 
	 * @param customerName
	 * @param customerLastName
	 * @return
	 */
	@PostMapping("/searchCustomerByFullName")
	public List<Customer> searchCustomerByFullName(@RequestParam String customerName,
			@RequestParam String customerLastName) {
		return customerService.findByCustomerNameAndCustomerLastName(customerName, customerLastName);
	}

	/**
	 * Metodo para buscar por nombre completo y DNI
	 * 
	 * @param customerName
	 * @param customerLastName
	 * @param document
	 * @return
	 */
	@PostMapping("/searchCustomerByFullNameAndDocument")
	public List<Customer> searchCustomerByFullNameAndDocument(@RequestParam String customerName,
			@RequestParam String customerLastName, @RequestParam String document) {
		return customerService.findByCustomerNameAndCustomerLastNameAndDocument(customerName, customerLastName,
				document);
	}

	/**
	 * Metodo para gestionar errores
	 * 
	 * @param res
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public String checkExceptions(BindingResult res) {

		String output = "";
		if (res.hasErrors()) {
			output = "/showErrorView";
		}
		return output;
	}
}
