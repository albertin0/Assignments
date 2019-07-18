package microservice.POC.SPQR.jwt;

import microservice.POC.SPQR.models.User;
import microservice.POC.SPQR.models.UserElastic;
import microservice.POC.SPQR.repository.UserElasticRepositoryUsingTemplate;
import microservice.POC.SPQR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserElasticRepositoryUsingTemplate userElasticRepositoryUsingTemplate;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        UserElastic userElastic = userElasticRepositoryUsingTemplate.findByUserName(userName);
        if (userElastic != null) {
            JwtUser jwtUser= new JwtUser(userElastic);
            return jwtUser;
        } else {
            return null;
        }
    }
}