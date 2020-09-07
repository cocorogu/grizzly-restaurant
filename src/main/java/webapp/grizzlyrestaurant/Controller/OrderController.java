package webapp.grizzlyrestaurant.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.grizzlyrestaurant.Entity.Category;
import webapp.grizzlyrestaurant.Entity.OrderProduct;
import webapp.grizzlyrestaurant.Entity.Orders;
import webapp.grizzlyrestaurant.Entity.Product;
import webapp.grizzlyrestaurant.Entity.Reservation;
import webapp.grizzlyrestaurant.Entity.User;
import webapp.grizzlyrestaurant.Service.OrderProductService;
import webapp.grizzlyrestaurant.Service.OrderService;
import webapp.grizzlyrestaurant.Service.ProductService;
import webapp.grizzlyrestaurant.Service.UserService;

@Controller
public class OrderController {
	
	@Autowired
	OrderProductService orderProductService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/order")
	public String showMenuForOrder(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			return "/login";
		} else {
			User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
			model.addAttribute("loggedUser", loggedUser);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			model.addAttribute("productList", productList);
			try {
				Orders verifyOrder = orderService.findOneByUser(loggedUser);

				if(verifyOrder.getFinishDate() == null || verifyOrder == null) {
					model.addAttribute("order", verifyOrder);
					return "products";
				} else {
					
				}
					Orders order = new Orders();
					order.setUser(loggedUser);
					orderService.add(order);
					model.addAttribute("order", order);
					return "products";
				}
			catch (Exception e) {
				Orders order = new Orders();
				order.setUser(loggedUser);
				orderService.add(order);
				model.addAttribute("order", order);
				return "products";
			}
		}
	}
	
	@GetMapping("/drinks")
	public String showDrinksMenuForOrder(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			return "/login";
		} else {
			User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
			model.addAttribute("loggedUser", loggedUser);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> drinksList = new ArrayList<>();
			
			for (Product p : productList) {
				Category c = p.getCategory();
				if (c.getId() == 5) {
					drinksList.add(p);
				}
			}
			
			model.addAttribute("productList", drinksList);
			
			try {
				Orders verifyOrder = orderService.findOneByUser(loggedUser);

				if(verifyOrder.getFinishDate() == null || verifyOrder == null) {
					model.addAttribute("order", verifyOrder);
					return "drinks";
				} else {
					
				}
					Orders order = new Orders();
					order.setUser(loggedUser);
					orderService.add(order);
					model.addAttribute("order", order);
					
					String notification = "";
					model.addAttribute("notification", notification);
					
					return "drinks";
				}
			catch (Exception e) {
				Orders order = new Orders();
				order.setUser(loggedUser);
				orderService.add(order);
				model.addAttribute("order", order);
				return "drinks";
			}
		}
	}
	
	@GetMapping("/sandwich")
	public String showSandwichMenuForOrder(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			return "/login";
		} else {
			User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
			model.addAttribute("loggedUser", loggedUser);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> sandwichList = new ArrayList<>();
			
			for (Product p : productList) {
				Category c = p.getCategory();
				if (c.getId() == 1) {
					sandwichList.add(p);
				}
			}
			
			model.addAttribute("productList", sandwichList);
			
			try {
				Orders verifyOrder = orderService.findOneByUser(loggedUser);

				if(verifyOrder.getFinishDate() == null || verifyOrder == null) {
					model.addAttribute("order", verifyOrder);
					return "sandwich";
				} else {
					
				}
					Orders order = new Orders();
					order.setUser(loggedUser);
					orderService.add(order);
					model.addAttribute("order", order);
					
					String notification = "";
					model.addAttribute("notification", notification);
					
					return "sandwich";
				}
			catch (Exception e) {
				Orders order = new Orders();
				order.setUser(loggedUser);
				orderService.add(order);
				model.addAttribute("order", order);
				return "sandwich";
			}
		}
	}
	
	@RequestMapping("/saveOrderProduct/sandwich/{id}") 
	public String saveOrderProductSandwich(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		
		Orders order = orderService.findOneByUser(loggedUser);
		
		OrderProduct op = new OrderProduct();
		op.setOrder(orderService.findOneByUser(loggedUser));
		op.setProduct(p);
		orderProductService.add(op);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		List<Product> sandwichList = new ArrayList<>();
		
		for (Product product : productList) {
			Category c = product.getCategory();
			if (c.getId() == 1) {
				sandwichList.add(product);
			}
		}
		
		String notification = "Ai adăugat produsul " + p.getName() + " la comanda ta!";
		model.addAttribute("notification", notification);
		
		model.addAttribute("productList", sandwichList);
		
		return "sandwich";
	}
	
	@RequestMapping("/deleteOrderProduct/sandwich/{id}") 
	public String deleteOrderProductSandwich(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		Orders order = orderService.findOneByUser(loggedUser);
		List<OrderProduct> orderProductList = orderProductService.findAll();
		List<OrderProduct> orderSpecificProductList = new ArrayList<>();
		
		for(OrderProduct orderProduct : orderProductList) {
			Product newProduct = orderProduct.getProduct();
			if (newProduct.getId() == p.getId()) {
				orderSpecificProductList.add(orderProduct);
			}
		}
		
		if (orderSpecificProductList.isEmpty()) {
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> sandwichList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 1) {
					sandwichList.add(product);
				}
			}
			
			String notification = "Produsul " + p.getName() + " nu există în comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", sandwichList);
			
			return "sandwich";
		} 
		else 
		{
			OrderProduct deletedOrderProduct = orderSpecificProductList.get(orderSpecificProductList.size() - 1);
			orderProductService.delete(deletedOrderProduct);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> sandwichList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 1) {
					sandwichList.add(product);
				}
			}
			
			String notification = "Ai șters produsul " + p.getName() + " din comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", sandwichList);
			
			return "sandwich";
		}
	}
	
	@GetMapping("/meniuri")
	public String showMenusMenuForOrder(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			return "/login";
		} else {
			User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
			model.addAttribute("loggedUser", loggedUser);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> menusList = new ArrayList<>();
			
			for (Product p : productList) {
				Category c = p.getCategory();
				if (c.getId() == 2) {
					menusList.add(p);
				}
			}
			
			model.addAttribute("productList", menusList);
			
			try {
				Orders verifyOrder = orderService.findOneByUser(loggedUser);

				if(verifyOrder.getFinishDate() == null || verifyOrder == null) {
					model.addAttribute("order", verifyOrder);
					return "meniuri";
				} else {
					
				}
					Orders order = new Orders();
					order.setUser(loggedUser);
					orderService.add(order);
					model.addAttribute("order", order);
					
					String notification = "";
					model.addAttribute("notification", notification);
					
					return "meniuri";
				}
			catch (Exception e) {
				Orders order = new Orders();
				order.setUser(loggedUser);
				orderService.add(order);
				model.addAttribute("order", order);
				return "meniuri";
			}
		}
	}
	
	@RequestMapping("/saveOrderProduct/meniuri/{id}") 
	public String saveOrderProductMenus(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		
		Orders order = orderService.findOneByUser(loggedUser);
		
		OrderProduct op = new OrderProduct();
		op.setOrder(orderService.findOneByUser(loggedUser));
		op.setProduct(p);
		orderProductService.add(op);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		List<Product> menusList = new ArrayList<>();
		
		for (Product product : productList) {
			Category c = product.getCategory();
			if (c.getId() == 2) {
				menusList.add(product);
			}
		}
		
		String notification = "Ai adăugat produsul " + p.getName() + " la comanda ta!";
		model.addAttribute("notification", notification);
		
		model.addAttribute("productList", menusList);
		
		return "meniuri";
	}
	
	@RequestMapping("/deleteOrderProduct/meniuri/{id}") 
	public String deleteOrderProductMenus(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		Orders order = orderService.findOneByUser(loggedUser);
		List<OrderProduct> orderProductList = orderProductService.findAll();
		List<OrderProduct> orderSpecificProductList = new ArrayList<>();
		
		for(OrderProduct orderProduct : orderProductList) {
			Product newProduct = orderProduct.getProduct();
			if (newProduct.getId() == p.getId()) {
				orderSpecificProductList.add(orderProduct);
			}
		}
		
		if (orderSpecificProductList.isEmpty()) {
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> menusList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 2) {
					menusList.add(product);
				}
			}
			
			String notification = "Produsul " + p.getName() + " nu există în comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", menusList);
			
			return "meniuri";
		} 
		else 
		{
			OrderProduct deletedOrderProduct = orderSpecificProductList.get(orderSpecificProductList.size() - 1);
			orderProductService.delete(deletedOrderProduct);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> menusList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 2) {
					menusList.add(product);
				}
			}
			
			String notification = "Ai șters produsul " + p.getName() + " din comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", menusList);
			
			return "meniuri";
		}
	}
	
	@GetMapping("/garnituri")
	public String showGarnituriMenuForOrder(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			return "/login";
		} else {
			User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
			model.addAttribute("loggedUser", loggedUser);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> garnituriList = new ArrayList<>();
			
			for (Product p : productList) {
				Category c = p.getCategory();
				if (c.getId() == 3) {
					garnituriList.add(p);
				}
			}
			
			model.addAttribute("productList", garnituriList);
			
			try {
				Orders verifyOrder = orderService.findOneByUser(loggedUser);

				if(verifyOrder.getFinishDate() == null || verifyOrder == null) {
					model.addAttribute("order", verifyOrder);
					return "garnituri";
				} else {
					
				}
					Orders order = new Orders();
					order.setUser(loggedUser);
					orderService.add(order);
					model.addAttribute("order", order);
					
					String notification = "";
					model.addAttribute("notification", notification);
					
					return "garnituri";
				}
			catch (Exception e) {
				Orders order = new Orders();
				order.setUser(loggedUser);
				orderService.add(order);
				model.addAttribute("order", order);
				return "garnituri";
			}
		}
	}
	
	@RequestMapping("/saveOrderProduct/garnituri/{id}") 
	public String saveOrderProductGarnituri(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		
		Orders order = orderService.findOneByUser(loggedUser);
		
		OrderProduct op = new OrderProduct();
		op.setOrder(orderService.findOneByUser(loggedUser));
		op.setProduct(p);
		orderProductService.add(op);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		List<Product> garnituriList = new ArrayList<>();
		
		for (Product product : productList) {
			Category c = product.getCategory();
			if (c.getId() == 3) {
				garnituriList.add(product);
			}
		}
		
		String notification = "Ai adăugat produsul " + p.getName() + " la comanda ta!";
		model.addAttribute("notification", notification);
		
		model.addAttribute("productList", garnituriList);
		
		return "garnituri";
	}
	
	@RequestMapping("/deleteOrderProduct/garnituri/{id}") 
	public String deleteOrderProductGarnituri(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		Orders order = orderService.findOneByUser(loggedUser);
		List<OrderProduct> orderProductList = orderProductService.findAll();
		List<OrderProduct> orderSpecificProductList = new ArrayList<>();
		
		for(OrderProduct orderProduct : orderProductList) {
			Product newProduct = orderProduct.getProduct();
			if (newProduct.getId() == p.getId()) {
				orderSpecificProductList.add(orderProduct);
			}
		}
		
		if (orderSpecificProductList.isEmpty()) {
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> garnituriList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 3) {
					garnituriList.add(product);
				}
			}
			
			String notification = "Produsul " + p.getName() + " nu există în comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", garnituriList);
			
			return "garnituri";
		} 
		else 
		{
			OrderProduct deletedOrderProduct = orderSpecificProductList.get(orderSpecificProductList.size() - 1);
			orderProductService.delete(deletedOrderProduct);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> garnituriList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 3) {
					garnituriList.add(product);
				}
			}
			
			String notification = "Ai șters produsul " + p.getName() + " din comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", garnituriList);
			
			return "garnituri";
		}
	}
	
	@GetMapping("/salate")
	public String showSaladMenuForOrder(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getName() == null) {
			return "/login";
		} else {
			User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
			model.addAttribute("loggedUser", loggedUser);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> saladList = new ArrayList<>();
			
			for (Product p : productList) {
				Category c = p.getCategory();
				if (c.getId() == 4) {
					saladList.add(p);
				}
			}
			
			model.addAttribute("productList", saladList);
			
			try {
				Orders verifyOrder = orderService.findOneByUser(loggedUser);

				if(verifyOrder.getFinishDate() == null || verifyOrder == null) {
					model.addAttribute("order", verifyOrder);
					return "salate";
				} else {
					
				}
					Orders order = new Orders();
					order.setUser(loggedUser);
					orderService.add(order);
					model.addAttribute("order", order);
					
					String notification = "";
					model.addAttribute("notification", notification);
					
					return "salate";
				}
			catch (Exception e) {
				Orders order = new Orders();
				order.setUser(loggedUser);
				orderService.add(order);
				model.addAttribute("order", order);
				return "salate";
			}
		}
	}
	
	@RequestMapping("/saveOrderProduct/salate/{id}") 
	public String saveOrderProductSalad(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		
		Orders order = orderService.findOneByUser(loggedUser);
		
		OrderProduct op = new OrderProduct();
		op.setOrder(orderService.findOneByUser(loggedUser));
		op.setProduct(p);
		orderProductService.add(op);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		List<Product> saladList = new ArrayList<>();
		
		for (Product product : productList) {
			Category c = product.getCategory();
			if (c.getId() == 4) {
				saladList.add(product);
			}
		}
		
		String notification = "Ai adăugat produsul " + p.getName() + " la comanda ta!";
		model.addAttribute("notification", notification);
		
		model.addAttribute("productList", saladList);
		
		return "salate";
	}
	
	@RequestMapping("/deleteOrderProduct/salate/{id}") 
	public String deleteOrderProductSalads(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		Orders order = orderService.findOneByUser(loggedUser);
		List<OrderProduct> orderProductList = orderProductService.findAll();
		List<OrderProduct> orderSpecificProductList = new ArrayList<>();
		
		for(OrderProduct orderProduct : orderProductList) {
			Product newProduct = orderProduct.getProduct();
			if (newProduct.getId() == p.getId()) {
				orderSpecificProductList.add(orderProduct);
			}
		}
		
		if (orderSpecificProductList.isEmpty()) {
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> saladList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 4) {
					saladList.add(product);
				}
			}
			
			String notification = "Produsul " + p.getName() + " nu există în comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", saladList);
			
			return "salate";
		} 
		else 
		{
			OrderProduct deletedOrderProduct = orderSpecificProductList.get(orderSpecificProductList.size() - 1);
			orderProductService.delete(deletedOrderProduct);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> saladList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 4) {
					saladList.add(product);
				}
			}
			
			String notification = "Ai șters produsul " + p.getName() + " din comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", saladList);
			
			return "salate";
		}
	}
	
	@RequestMapping("/saveOrderProduct/{id}") 
	public String saveOrderProduct(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		
		Orders order = orderService.findOneByUser(loggedUser);
		
		OrderProduct op = new OrderProduct();
		op.setOrder(orderService.findOneByUser(loggedUser));
		op.setProduct(p);
		orderProductService.add(op);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		
		return "products";
	}
	
	@RequestMapping("/saveOrderProduct/drinks/{id}") 
	public String saveOrderProductDrinks(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		
		Orders order = orderService.findOneByUser(loggedUser);
		
		OrderProduct op = new OrderProduct();
		op.setOrder(orderService.findOneByUser(loggedUser));
		op.setProduct(p);
		orderProductService.add(op);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		List<Product> drinksList = new ArrayList<>();
		
		for (Product product : productList) {
			Category c = product.getCategory();
			if (c.getId() == 5) {
				drinksList.add(product);
			}
		}
		
		String notification = "Ai adăugat produsul " + p.getName() + " la comanda ta!";
		model.addAttribute("notification", notification);
		
		model.addAttribute("productList", drinksList);
		
		return "drinks";
	}
	
	@RequestMapping("/deleteOrderProduct/drinks/{id}") 
	public String deleteOrderProductDrinks(@PathVariable("id") Long product_id, Model model) {
		
		Optional<Product> productEntity =  productService.findOne(product_id);
		Product p = productEntity.get();
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		Orders order = orderService.findOneByUser(loggedUser);
		List<OrderProduct> orderProductList = orderProductService.findAll();
		List<OrderProduct> orderSpecificProductList = new ArrayList<>();
		
		for(OrderProduct orderProduct : orderProductList) {
			Product newProduct = orderProduct.getProduct();
			if (newProduct.getId() == p.getId()) {
				orderSpecificProductList.add(orderProduct);
			}
		}
		
		if (orderSpecificProductList.isEmpty()) {
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> drinksList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 5) {
					drinksList.add(product);
				}
			}
			
			String notification = "Produsul " + p.getName() + " nu există în comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", drinksList);
			
			return "drinks";
		} 
		else 
		{
			OrderProduct deletedOrderProduct = orderSpecificProductList.get(orderSpecificProductList.size() - 1);
			orderProductService.delete(deletedOrderProduct);
			
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			List<Product> productList = productService.findAll();
			List<Product> drinksList = new ArrayList<>();
			
			for (Product product : productList) {
				Category c = product.getCategory();
				if (c.getId() == 5) {
					drinksList.add(product);
				}
			}
			
			String notification = "Ai șters produsul " + p.getName() + " din comanda ta!";
			model.addAttribute("notification", notification);
			
			model.addAttribute("productList", drinksList);
			
			return "drinks";
		}
	}
	
	@GetMapping("/myorders")
	public String showMyOrders(Model model) {
		long total = 0;
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		model.addAttribute("loggedUser", loggedUser);
		

		Orders verifyOrder = orderService.findOneByUser(loggedUser);
		model.addAttribute("order", verifyOrder);
		
		List<OrderProduct> orderProductList = new ArrayList<>();
		orderProductList = orderProductService.findAll();
		
		List<OrderProduct> userOrderProductList = new ArrayList<>();
		List<Product> orderedProducts = new ArrayList<>();
		
		for (OrderProduct op : orderProductList) {
			if(op.getOrder() == verifyOrder) {
				userOrderProductList.add(op);
			}
		}
		
		for (OrderProduct op : userOrderProductList) {
			orderedProducts.add(op.getProduct());
			total += Integer.parseInt(op.getProduct().getPrice());
		}
		
		model.addAttribute("orderedProducts", orderedProducts);
		model.addAttribute("total", total);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		return "/myorders";
	}
	
	
	@GetMapping("/deleteorder/{id}")
	public String deleteOrder(@PathVariable Long id, Model model) {
		
		Optional<Orders> optionalEntity = orderService.findOne(id);
		Orders order = optionalEntity.get();
		
		List<OrderProduct> orderProductList = new ArrayList<>();
		orderProductList = orderProductService.findAll();
		
		for (OrderProduct op : orderProductList) {
			Orders o = op.getOrder();
			if(o.getId() == order.getId()) {
				orderProductService.delete(op);
			}
		}
		
		orderService.delete(order);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		
		model.addAttribute("user", loggedUser);
		
		return "home-loggedin";
	}
	
	@GetMapping("/sendorder/{id}")
	public String sendOrder(@PathVariable Long id, Model model) {
		
		int total = 0;
		Optional<Orders> optionalEntity = orderService.findOne(id);
		Orders order = optionalEntity.get();
		
		
		/*DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String localDate = dtf.format(now);
		System.out.println(localDate);
	    Date date = new Date();  
	    System.out.println(date);
	    
		order.setFinishDate(new Date());
		System.out.println(order.getFinishDate());*/
		
		List<OrderProduct> orderProductList = new ArrayList<>();
		orderProductList = orderProductService.findAll();
		
		List<OrderProduct> userOrderProductList = new ArrayList<>();
		List<Product> orderedProducts = new ArrayList<>();
		
		for (OrderProduct op : orderProductList) {
			if(op.getOrder() == order) {
				userOrderProductList.add(op);
			}
		}
		
		for (OrderProduct op : userOrderProductList) {
			orderedProducts.add(op.getProduct());
			total += Integer.parseInt(op.getProduct().getPrice());
		}
		
		order.setTotal(""+total);
		order.setFinishDate(new Date());
	    orderService.save(order);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		model.addAttribute("user", loggedUser);
		
		return "confirmation";
	}
	
	@ModelAttribute("peopleList")
	public List<String> getPeopleList() {
		List<String> peopleList = new ArrayList<>();
		peopleList.add("1");
		peopleList.add("2");
		peopleList.add("3");
		peopleList.add("4");
		return peopleList;
	}
	
	@ModelAttribute("hourList")
	public List<String> getHourList() {
		List<String> hourList = new ArrayList<>();
		hourList.add("12:00 - 14:00");
		hourList.add("14:00 - 16:00");
		hourList.add("16:00 - 18:00");
		hourList.add("18:00 - 20:00");
		hourList.add("20:00 - 22:00");
		return hourList;
	}
}
	
