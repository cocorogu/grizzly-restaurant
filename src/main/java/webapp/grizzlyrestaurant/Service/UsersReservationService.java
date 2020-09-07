package webapp.grizzlyrestaurant.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Entity.User;
import webapp.grizzlyrestaurant.Entity.UsersReservation;
import webapp.grizzlyrestaurant.Repository.UsersReservationRepository;

@Service
public class UsersReservationService {
	
	@Autowired
	UsersReservationRepository usersReservationRepository;
	
	public UsersReservation add(UsersReservation r) {
		return usersReservationRepository.save(r);
	}
	
	public List<UsersReservation> findAll(){
		return usersReservationRepository.findAll();
	}
	
	public Optional<UsersReservation> findOne(Long id) {
		return usersReservationRepository.findById(id);
	}
	
	public void delete(UsersReservation r) {
		usersReservationRepository.delete(r);
	}
	
	public UsersReservation findOneByUser(User user) {
		Optional<UsersReservation> optionalEntity = usersReservationRepository.findByUser(user);
		UsersReservation usersReservationEntity = optionalEntity.get();
		return usersReservationEntity;
	}
}


