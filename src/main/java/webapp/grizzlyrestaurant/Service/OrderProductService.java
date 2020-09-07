package webapp.grizzlyrestaurant.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Repository.OrderProductRepository;
import webapp.grizzlyrestaurant.Entity.OrderProduct;
import webapp.grizzlyrestaurant.Entity.Orders;
import webapp.grizzlyrestaurant.Entity.Product;
import webapp.grizzlyrestaurant.Entity.User;

@Service
public class OrderProductService {
	
	@Autowired
	OrderProductRepository orderProductRepository;
	
	public OrderProduct add(OrderProduct o) {
		return orderProductRepository.save(o);
	}
	
	public List<OrderProduct> findAll(){
		return orderProductRepository.findAll();
	}
	
	public Optional<OrderProduct> findOne(Long id) {
		return orderProductRepository.findById(id);
	}
	
	public void delete(OrderProduct o) {
		orderProductRepository.delete(o);
	}
}

