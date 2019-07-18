package microservice.POC.SPQR.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import microservice.POC.SPQR.exceptions.NotFoundException;
import microservice.POC.SPQR.models.User;
//import microservice.POC.SPQR.models.UserElastic;
//import microservice.POC.SPQR.repository.UserElasticRepository;
import microservice.POC.SPQR.models.UserElastic;
import microservice.POC.SPQR.repository.UserElasticRepositoryUsingTemplate;
import microservice.POC.SPQR.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

//@Component
@Service
public class JwtTokenUtil implements Serializable {

    private final static long serialVersionUID = 1L;
    private final static Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private Clock clock = DefaultClock.INSTANCE;

    @Value("${jwt.secret}")
    private String secret;

    private Map<String, List<String>> userTokensMap = new HashMap();

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserElasticRepositoryUsingTemplate userElasticRepositoryUsingTemplate;

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateToken(String userName, HttpServletRequest request) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userName, request);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, HttpServletRequest request) {
        final  Date createdDate = clock.now();
        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        // Add token to DB
        User user = userRepository.findByUserName(subject);

        UserElastic userElastic = userElasticRepositoryUsingTemplate.findByUserName(subject);

//        UserElastic userElastic = userElasticRepository.findByUserName(subject);
        //List<String> userTokens = getUserTokensMap().get(subject);
        HashMap<String,String> userTokens = userElastic.getToken();
        if(userTokens == null)  {
            userTokens = new HashMap<>();
        }
        userTokens.put(request.getSession().getId(),token);

        user.setToken(userTokens);
        userRepository.save(user);

        userElasticRepositoryUsingTemplate.deleteUser(userElastic);
        userElastic.setToken(userTokens);
        userElasticRepositoryUsingTemplate.createUser(userElastic);

        return token;
    }

    public Boolean isTokenValid(String token) throws NotFoundException {
        if (token.isEmpty()) {
            logger.info("token is null...");
            throw new NotFoundException();
        }
        final String userName = getUserNameFromToken(token);
        UserElastic userElastic = userElasticRepositoryUsingTemplate.findByUserName(userName);
        if (userElastic != null)
            return (userName.equals(userElastic.getUserName()) && userElastic.getToken().values().contains(token));
        return false;
    }

    public void invalidateToken(String token) {
        String userName = getUserNameFromToken(token);
//        List<String> userTokens = getUserTokensMap().get(userName);
        HashMap<String,String> userToken = userElasticRepositoryUsingTemplate.findByUserName(userName).getToken();
        for(String k:userToken.keySet())   {
            if(userToken.get(k).equals(token)) {
                userToken.remove(k);
            }
        }
//        userTokens.remove(token);
        // Remove token from DB
        UserElastic userElastic = userElasticRepositoryUsingTemplate.findByUserName(userName);
        User user = userRepository.findByUserName(userName);

        user.setToken(userToken);
        userRepository.save(user);

        userElasticRepositoryUsingTemplate.deleteUser(userElastic);
        userElastic.setToken(userToken);
        userElasticRepositoryUsingTemplate.createUser(userElastic);

    }
}
