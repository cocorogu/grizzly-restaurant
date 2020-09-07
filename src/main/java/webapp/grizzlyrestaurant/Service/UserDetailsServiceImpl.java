package webapp.grizzlyrestaurant.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import webapp.grizzlyrestaurant.Entity.Authority;
import webapp.grizzlyrestaurant.Repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
	
    @Override
     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
    	webapp.grizzlyrestaurant.Entity.User appUser = userRepository.findByPhoneNumber(username).orElseThrow(() -> new UsernameNotFoundException("Nu exista acest user."));
		
    List grantList = new ArrayList();
    for (Authority authority: appUser.getAuthority()) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority());
            grantList.add(grantedAuthority);
    }
		
    UserDetails user = (UserDetails) new User(appUser.getPhoneNumber(), appUser.getPassword(), grantList);
         return user;
    }
}
