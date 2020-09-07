package webapp.grizzlyrestaurant.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Repository.OrdersRepository;
import webapp.grizzlyrestaurant.Entity.Orders;
import webapp.grizzlyrestaurant.Entity.User;

@Service
public class OrderService {
	
	@Autowired
	OrdersRepository ordersRepository;
	
	public Orders add(Orders o) {
		return ordersRepository.save(o);
	}
	
	public Orders save(Orders o) {
		return ordersRepository.save(o);
	}
	
	public List<Orders> findAll(){
		return ordersRepository.findAll();
	}
	
	public Optional<Orders> findOne(Long id) {
		return ordersRepository.findById(id);
	}
	
	public void delete(Orders o) {
		ordersRepository.delete(o);
	}
	
	public Orders findOneByUser(User user) {
		Optional<Orders> optionalEntity = ordersRepository.findByUser(user);
		Orders ordersEntity = optionalEntity.get();
		return ordersEntity;
	}
}

