package delivery.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public JWTFunctionHelper jwtFunctionHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = null;
        String requestHeader = request.getHeader("Authorization");
        logger.info("Inside doFilterInternal");

        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7);
            logger.info("Inside doFilterInternal:get username from the token");
            username = this.jwtFunctionHelper.getUsernameFromToken(token);

        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //fetch user detail from user retrieve from the token
            logger.info("fetch userDetails from in memory using user retrieve from the token");
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            logger.info("fetch userDetails from in memory using user retrieve from the token userDetails"+userDetails.toString());
            Boolean validateToken = this.jwtFunctionHelper.validateToken(token, userDetails);
            logger.info("Token validation!!"+ validateToken);

            if (validateToken){
                //set the authentication, valid tokens will trigger the authentication process.
                logger.info("set the authentication if token is valid!!");
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                logger.info("Validation fails !!");
            }
        }
        filterChain.doFilter(request, response);
    }
}
