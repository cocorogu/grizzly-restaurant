package webapp.grizzlyrestaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.grizzlyrestaurant.Entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {

}