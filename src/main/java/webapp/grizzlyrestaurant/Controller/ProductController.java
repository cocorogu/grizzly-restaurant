package webapp.grizzlyrestaurant.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import webapp.grizzlyrestaurant.Entity.Product;
import webapp.grizzlyrestaurant.Entity.Reservation;
import webapp.grizzlyrestaurant.Service.ProductService;

@Controller
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/menu")
	public String showMenu(Model model) {
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);
		
		return "products";
	}
	
	
}
	
