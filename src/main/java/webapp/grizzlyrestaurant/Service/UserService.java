package webapp.grizzlyrestaurant.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Repository.UserRepository;
import webapp.grizzlyrestaurant.Entity.User;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User add(User u) {
		return userRepository.save(u);
	}
	
	public Iterable<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findOne(Long id) {
		Optional<User> optionalEntity = userRepository.findById(id);
		User userEntity = optionalEntity.get();
		return userEntity;
	}
	
	public User findOneByPhoneNumber(String phoneNumber) {
		Optional<User> optionalEntity = userRepository.findByPhoneNumber(phoneNumber);
		User userEntity = optionalEntity.get();
		return userEntity;
	}
	
	public void delete(User u) {
		userRepository.delete(u);
	}
	
	public void setUserAuthority(Long id1, Long id2) {
		userRepository.setUserAuthority(id1, id2);
	}
}

