package microservice.POC.SPQR.jwt;

import microservice.POC.SPQR.GraphQLService;
//import microservice.POC.SPQR.LoggedInUserBean;
import microservice.POC.SPQR.jwt.JwtTokenUtil;
import microservice.POC.SPQR.models.User;
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
import java.util.Optional;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private String authToken = null;

//    @Autowired
//    LoggedInUserBean loggedInUserBean;

    @Autowired
    UserRepository userRepository;

    public JwtFilter() {

    }

    public JwtFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
//        Optional<HttpServletRequest> optReq = Optional.of(request);
//        authToken = optReq.map(req -> req.getHeader("Authorization")).filter(token -> !token.isEmpty()).orElse(null);
//        if(loggedInUserBean!=null && loggedInUserBean.getLoggedInUser()!=null && loggedInUserBean.getLoggedInUser().getToken()!=null
//        && loggedInUserBean.getLoggedInUser().getToken().size()>0)  {
//            authToken = loggedInUserBean.getLoggedInUser().getToken().get(0);
//        }
        List<User> users = null;
        if(userRepository!=null) {
            users = userRepository.findAll();
        }
        if(users!=null) {
            for (User user : users) {
                if (user.getToken() != null && user.getToken().keySet().contains(request.getSession().getId())) {
                    authToken = user.getToken().get(request.getSession().getId());
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
