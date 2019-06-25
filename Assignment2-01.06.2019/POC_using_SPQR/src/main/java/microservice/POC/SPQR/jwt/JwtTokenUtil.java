package microservice.POC.SPQR.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import microservice.POC.SPQR.exceptions.NotFoundException;
import microservice.POC.SPQR.models.User;
import microservice.POC.SPQR.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
        //List<String> userTokens = getUserTokensMap().get(subject);
        HashMap<String,String> userTokens = user.getToken();
        if (userTokens != null)
            userTokens.put(request.getSession().getId(),token);
        else {
            userTokens = new HashMap<>();
            userTokens.put(request.getSession().getId(), token);
        }
        //getUserTokensMap().put(subject, userTokens);
        user.setToken(userTokens);
        userRepository.save(user);
        return token;
    }

//    public Map<String, List<String>> getUserTokensMap() {
//        if (userTokensMap.isEmpty()) {
//            userRepository.findAll().forEach((user) -> {
//                userTokensMap.put(user.getUserName(), user.getToken());
//            });
//        }
//        return userTokensMap;
//    }

    public Boolean isTokenValid(String token) throws NotFoundException {
        if (token.isEmpty()) {
            logger.info("token is null...");
            throw new NotFoundException();
        }
        final String userName = getUserNameFromToken(token);
        User user = userRepository.findByUserName(userName);
        if (user != null)
//            return (userName.equals(user.getUserName()) && getUserTokensMap().get(userName).contains(token));
            return (userName.equals(user.getUserName()) && user.getToken().values().contains(token));
        return false;
    }

    public void invalidateToken(String token) {
        String userName = getUserNameFromToken(token);
//        List<String> userTokens = getUserTokensMap().get(userName);
        HashMap<String,String> userTokens = userRepository.findByUserName(userName).getToken();
        for(String k:userTokens.keySet())   {
            if(userTokens.get(k).equals(token))
                userTokens.remove(k);
        }
//        userTokens.remove(token);
        // Remove token from DB
        User user = userRepository.findByUserName(userName);
        user.setToken(userTokens);
        userRepository.save(user);
    }

}
