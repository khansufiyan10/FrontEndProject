package com.niit.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;
import com.niit.dao.CartItemDAO;
import com.niit.dao.ProductDAO;
import com.niit.dao.UserDAO;
import com.niit.model.CartItem;
import com.niit.model.Product;
import com.niit.model.User;

@Controller
@RequestMapping("/cartitem")
public class CartItemController 
{
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	CartItemDAO cartItemDAO;
	
	@RequestMapping("/add/{id}")
	public String addCart(@PathVariable("id") int productid)
	{
		Product p=new Product();
		p.setProductid(productid);
		
		p=productDAO.displayProductById(p);
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) 
		{
		  UserDetails user= ((UserDetails)principal);
		  User u=new User();
		  u.setUsername(user.getUsername());
		  u=userDAO.displayUserByUserName(u);
		  CartItem cartItem=new CartItem();
			cartItem.setProduct(p);
			cartItem.setQuantity(0);
			cartItem.setTotalprice(0);
			cartItem.setUser(u);
			
			cartItemDAO.addCart(cartItem);
		}
		else
		{
			System.out.println("Cart Item Not Added");
		}
		
		return "redirect:/cartitem/display";
		
		
	}
	
	@RequestMapping("/delete/{cartitemid}")
	public String deleteCart(@PathVariable("cartitemid") int cartitemid)
	{
		CartItem cartItem=new CartItem();
		cartItem.setCartitemid(cartitemid);
		
		cartItemDAO.deleteCart(cartItem);
		System.out.println("abc");
		return "redirect:/cartitem/display";
		
	}
	

 
	
	
	@RequestMapping("/disp/{id}")
	public String displayProduct(@PathVariable("id") int cartitemid, ModelMap map)
	{
		CartItem cartItem=new CartItem();
		cartItem.setCartitemid(cartitemid);
		
		cartItem=cartItemDAO.displayCartById(cartItem);
		map.addAttribute("cartItem",cartItem);
		return "displaycartitem";
	}
	
	@RequestMapping("/display")
	public String displayCart(ModelMap map)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) 
		{
		  UserDetails user= ((UserDetails)principal);
		  User u=new User();
		  u.setUsername(user.getUsername());
		  u=userDAO.displayUserByUserName(u);
		  List<CartItem> cartItems=cartItemDAO.displayCartByUser(u);
		  
		  map.addAttribute("cartItems",cartItems);
		}
		else
		{
			System.out.println("No User Found to display cart!!!");
		}
		return "displaycartitems";
	}
}