package webapp.grizzlyrestaurant.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import webapp.grizzlyrestaurant.Service.UserService;
import webapp.grizzlyrestaurant.Entity.Reservation;
import webapp.grizzlyrestaurant.Entity.User;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping("/home")
	public String showHome(Model model) {
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User u = userService.findOneByPhoneNumber(authentication.getName());
		
		model.addAttribute("user", u);
		
		return "home-loggedin";
	}
	
	
	@GetMapping("/client-register")
	public String showRegisterClient(Model model) {
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		User u = new User();
		model.addAttribute("user", u);
		
		return "client-register";
	}
	
	@PostMapping("/registerclient")
	public String registerClient(User u, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			System.out.println("Result has errors!");
			return "client-register";
		}
		
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		u.setPassword(bCryptPasswordEncoder.encode(u.getPassword()));
		u.setEnabled(true);
		
		Date date = new Date();
		u.setRegDate(date);
		
		/*Authority a = new Authority();
		a.setAuthority("ROLE_USER");
		u.getAuthority().add(a);

		userService.setUserAuthority(u.getId());*/
		
		userService.add(u);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		model.addAttribute("user", u);
		
		return "login";
	}
	
	@GetMapping("/username")
	@ResponseBody
	public String currentUserName(Authentication authentication) {
		return authentication.getName();
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
