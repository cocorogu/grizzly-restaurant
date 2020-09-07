package webapp.grizzlyrestaurant.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Repository.ReservationRepository;
import webapp.grizzlyrestaurant.Entity.Reservation;

@Service
public class ReservationService {
	
	@Autowired
	ReservationRepository reservationRepository;
	
	public Reservation add(Reservation r) {
		return reservationRepository.save(r);
	}
	
	public List<Reservation> findAll(){
		return reservationRepository.findAll();
	}
	
	public Optional<Reservation> findOne(Long id) {
		return reservationRepository.findById(id);
	}
	
	public void delete(Reservation r) {
		reservationRepository.delete(r);
	}
	
}

