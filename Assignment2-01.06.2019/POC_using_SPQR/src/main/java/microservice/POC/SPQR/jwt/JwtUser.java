package microservice.POC.SPQR.jwt;

import microservice.POC.SPQR.models.User;
import microservice.POC.SPQR.models.UserElastic;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class JwtUser extends UserElastic implements UserDetails {

    private static final long serialVersionUID = 1L;

    public JwtUser(UserElastic userElastic) {
        super(userElastic);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.getRole()
                .stream()
//		.map(role-> new SimpleGrantedAuthority("ROLE_" + role.name())) //this one is used if role is ENUM
                .map(role-> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}

