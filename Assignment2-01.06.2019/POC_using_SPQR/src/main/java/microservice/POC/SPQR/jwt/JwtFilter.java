package microservice.POC.SPQR.jwt;

//import microservice.POC.SPQR.LoggedInUserBean;
import microservice.POC.SPQR.models.User;
import microservice.POC.SPQR.models.UserElastic;
import microservice.POC.SPQR.repository.UserElasticRepositoryUsingTemplate;
import microservice.POC.SPQR.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String authToken = null;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserElasticRepositoryUsingTemplate userElasticRepositoryUsingTemplate;

    public JwtFilter() {

    }

    public JwtFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        List<UserElastic> userElastics = null;
        if(userElasticRepositoryUsingTemplate!=null) {
            userElastics = userElasticRepositoryUsingTemplate.findAll();
        }
        if(userElastics!=null) {
            for (UserElastic userElastic : userElastics) {
                if (userElastic.getToken() != null && userElastic.getToken().keySet().contains(request.getSession().getId())) {
                    authToken = userElastic.getToken().get(request.getSession().getId());
                }
            }
        }
        if (authToken != null) {
            UserDetails userDetails = this.userDetailsService
                    .loadUserByUsername(jwtTokenUtil.getUserNameFromToken(authToken));
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null,
                    null, null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        }
    }

}
