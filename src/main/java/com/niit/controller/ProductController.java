package com.niit.controller;

import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import com.niit.dao.ProductDAO;
import com.niit.dao.UserDAO;
import com.niit.model.Product;
import com.niit.model.User;

@Controller
@RequestMapping("/product")
public class ProductController 
{
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	UserDAO userDAO;
	
	
	@GetMapping("/buy/{productid}")
	public void buyProduct(@PathVariable("productid") int productid,HttpServletResponse resp)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		User u=null;
		if (principal instanceof UserDetails) 
		{
		  UserDetails user= ((UserDetails)principal);
		  u=new User();
		  u.setUsername(user.getUsername());
		  u=userDAO.displayUserByUserName(u);
		}
	   try 
       {
           ApiContext context = ApiContext.create("test_mcSWiM9Y6RFa47eI3Kf7XgBiB2pgQl7ZjSM", "test_kDuEe7A28eQM6m5XuwaDfAOOV4QARRR1aIWH8LC3K7HnEwJglym8fTXpJa988EvAmWvJUEFx8RyboaTizIAaRvbSJ97JmcNFYbYWy1Z5lbU6y1pgUkZxmsUCpSO", ApiContext.Mode.TEST);
           Instamojo api = new InstamojoImpl(context);

           PaymentOrder order = new PaymentOrder();
           order.setName(u.getUsername());
           order.setEmail(u.getEmail());
           order.setPhone(u.getMobile());
           order.setCurrency("INR");
           order.setAmount((double)11);
           order.setDescription("This is a test transaction.");
           order.setRedirectUrl("https://7d6965f7.ngrok.io/LibraryDemo/displaybooks.jsp");
           order.setWebhookUrl("https://7d6965f7.ngrok.io/LibraryDemo/");
           order.setTransactionId(UUID.randomUUID().toString());

           PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
           resp.sendRedirect(paymentOrderResponse.getPaymentOptions().getPaymentUrl());
       }
       catch (Exception e) 
       {
           System.out.println(e);
       }
	} 
	
	@GetMapping("/add")
	public String addProduct(ModelMap map)
	{
		map.addAttribute("product",new Product());
		return "addproduct";
	}

	@PostMapping("/add")
	public String storeProduct(@ModelAttribute("product") Product product)
	{
		productDAO.addProduct(product);
		return "redirect:/product/display";
	}
	
	@RequestMapping("/display")
	public String displayProducts(ModelMap map)
	{
		map.addAttribute("products",productDAO.displayProducts());
		return "displayproducts";
	}
	
	@RequestMapping("/hightolow")
	public String sortPrice1(ModelMap map)
	{
		map.addAttribute("products",productDAO.displayProductByPriceDesc());
		return "displayproducts";
	}
	
	@RequestMapping("/lowtohigh")
	public String sortPrice2(ModelMap map)
	{
		map.addAttribute("products",productDAO.displayProductByPriceAsc());
		return "displayproducts";
	}
	
	@RequestMapping("/searchbyname")
	public String searchByName(@RequestParam("search") String name,ModelMap map)
	{
		map.addAttribute("products",productDAO.searchByName(name));
		return "displayproducts";
	}
	
	@RequestMapping("/display/{productid}")
	public String displayProduct(@PathVariable("productid") int productid, ModelMap map)
	{
		Product product=new Product();
		product.setProductid(productid);
		map.addAttribute("product",productDAO.displayProductById(product));
		return "displayproduct";
	}

	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable("id") int productid)
	{
		Product p=new Product();
		p.setProductid(productid);
		productDAO.deleteProduct(p);
		return "redirect:/product/display";
	}
	
	@RequestMapping("/edit/{productid}")
	public String editProduct(@PathVariable("productid") int productid,ModelMap map)
	{
		Product p=new Product();
		p.setProductid(productid);
		map.addAttribute("p",productDAO.displayProductById(p));
		return "addproduct";
	}
	
	@RequestMapping("/update")
	public String editProduct(@ModelAttribute("p") Product p)
	{
		productDAO.updateProduct(p);
		return "redirect:/product/display";
	}
}