package webapp.grizzlyrestaurant.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Repository.ProductRepository;
import webapp.grizzlyrestaurant.Entity.Product;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public Product add(Product p) {
		return productRepository.save(p);
	}
	
	public List<Product> findAll(){
		return productRepository.findAll();
	}
	
	public Optional<Product> findOne(Long id) {
		return productRepository.findById(id);
	}
	
	public void delete(Product p) {
		productRepository.delete(p);
	}
	
}