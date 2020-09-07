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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import webapp.grizzlyrestaurant.Entity.Product;
import webapp.grizzlyrestaurant.Entity.Reservation;
import webapp.grizzlyrestaurant.Entity.User;
import webapp.grizzlyrestaurant.Entity.UsersReservation;
import webapp.grizzlyrestaurant.Service.ReservationService;
import webapp.grizzlyrestaurant.Service.UserService;
import webapp.grizzlyrestaurant.Service.UsersReservationService;

@Controller
public class TwilioController {
	
	public static final String ACCOUNT_SID = "AC141f8b5146b5676bbaff2c81948507bf";
	public static final String AUTH_TOKEN = "dfa5f34351280247fcfbed8bee59a51c";
	
	@Autowired
	UserService userService;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	UsersReservationService usersReservationService;

	@GetMapping("/myreservations")
	public String showMyReservations(Model model) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		model.addAttribute("loggedUser", loggedUser);
		
		UsersReservation userReservations = usersReservationService.findOneByUser(loggedUser);
		Optional<Reservation> reservationEntity =  reservationService.findOne(userReservations.getReservation().getId());
		Reservation showReservation = reservationEntity.get();
		model.addAttribute("showReservation", showReservation);
		
		User u = new User();
		model.addAttribute("user", u);
		
		String invitationMessage = "";
		model.addAttribute("invitationMessage", invitationMessage);
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		return "/myreservations";
	}
	
	@PostMapping("/sendWhatsAppMessage")
	public String sendWhatsAppMessage(Model model, User u) {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findOneByPhoneNumber(authentication.getName());
		model.addAttribute("loggedUser", loggedUser);
		
		UsersReservation userReservations = usersReservationService.findOneByUser(loggedUser);
		Optional<Reservation> reservationEntity =  reservationService.findOne(userReservations.getReservation().getId());
		Reservation showReservation = reservationEntity.get();
		model.addAttribute("showReservation", showReservation);
	
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	       Message message = Message.creator(
	               new com.twilio.type.PhoneNumber("whatsapp:+40" + u.getPhoneNumber()),
	               new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
	               "Salut! Ați fost invitat la rezervarea creată de " + loggedUser.getLastName() + " pe data de " +
	               showReservation.getDay() + ", ora " + showReservation.getStartHour() + 
	               " la restaurantul Grizzly!").create();
		
		Reservation reservation = new Reservation();
		model.addAttribute("reservation", reservation);
		
		String invitationConfirmed = "Invitatia a fost trimisă!";
		model.addAttribute("invitationMessage", invitationConfirmed);
		
		return "/myreservations";
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
