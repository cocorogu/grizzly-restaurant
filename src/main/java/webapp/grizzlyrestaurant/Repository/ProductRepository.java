package webapp.grizzlyrestaurant.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webapp.grizzlyrestaurant.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>  {

}