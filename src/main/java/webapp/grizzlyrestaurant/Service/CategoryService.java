package webapp.grizzlyrestaurant.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Entity.Category;
import webapp.grizzlyrestaurant.Repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;
	
	public Category add(Category c) {
		return categoryRepository.save(c);
	}
	
	public Category save(Category c) {
		return categoryRepository.save(c);
	}
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
	
	public Optional<Category> findOne(Long id) {
		return categoryRepository.findById(id);
	}
	
	public void delete(Category c) {
		categoryRepository.delete(c);
	}
}

