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

import webapp.grizzlyrestaurant.Entity.Reservation;
import webapp.grizzlyrestaurant.Entity.User;
import webapp.grizzlyrestaurant.Entity.UsersReservation;
import webapp.grizzlyrestaurant.Service.ReservationService;
import webapp.grizzlyrestaurant.Service.UserService;
import webapp.grizzlyrestaurant.Service.UsersReservationService;

@Controller
public class ReservationController {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	UsersReservationService usersReservationService;
	
	@Autowired
	UserService userService;
	
	@GetMapping({"/", "/index"})
	public String index(Model model) {
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		return "index";
	}
	
	@GetMapping("/login")
	public String showLogin(Model model) {
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		return "login";
	}
	
	@PostMapping("/savereservation")
	public String saveReservation(Reservation r, Model model, BindingResult result) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getName() == null) {
			Reservation reservation = new Reservation();
			model.addAttribute("reservation", reservation);
			
			return "login";
		} else {
			
			String[] hours = r.getStartHour().split(" ");
			r.setStartHour(hours[0]);
			r.setEndHour(hours[2]);
			
			List<Reservation> reservationList = reservationService.findAll();
			int i = 0;
			for ( Reservation reservation : reservationList) {
				if (r.getStartHour() == reservation.getStartHour() && r.getDay() == reservation.getDay())
				{
					i++;
				}
			}
			
			if (i == 0) {
				reservationService.add(r);
				
				String phoneNumber = authentication.getName();
				User u = userService.findOneByPhoneNumber(phoneNumber);
				
				UsersReservation usersReservation = new UsersReservation();
				usersReservation.setReservation(r);
				usersReservation.setUser(u);
				usersReservation.setAdmin(true);
				
				usersReservationService.add(usersReservation);
				
				model.addAttribute("user", u);
				
				
				return "confirmation-reservation"; 
			} else {
				return "home-loggedin";
			}
		}
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
