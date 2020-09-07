package webapp.grizzlyrestaurant.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webapp.grizzlyrestaurant.Entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>  {
    public Optional<User> findByPhoneNumber(String phoneNumber);
    
    /*public void setUserAuthority(User u) {
    	@Query("insert into authorities_users (user_id, authority_id) values (" + u.getId() + ", 2");
    }*/
    
    
    
    @Query(value = "insert into authorities_users (user_id, authority_id) VALUES (:user_id, :authority_id)", nativeQuery = true)
    void setUserAuthority(@Param("user_id") Long id1, @Param("authority_id") Long id2);
}