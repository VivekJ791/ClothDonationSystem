package com.example.clothdonationsystem.config.jwt;

import com.example.clothdonationsystem.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        log.info("{Header}:",requestHeader);
        String username=null;
        String token=null;

        if(requestHeader!=null && requestHeader.startsWith("Bearer ")){
            token = requestHeader.substring(7);
            try{
                username= jwtUtils.getUserNameFromJwtToken(token);
            }catch (IllegalArgumentException e){
                log.info("Illegal Argument while fetching the username !!");
                e.printStackTrace();
            }catch (ExpiredJwtException e){
                log.info("Given jwt token is expired !!");
                e.printStackTrace();
            }catch (MalformedJwtException e){
                log.info("Some changed has done in token !! Invalid Token");
                e.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            log.info("invalid header value!!");
        }


        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            boolean validateToken= this.jwtUtils.validateJwtToken(token,userDetails);
            if(validateToken){
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else{
                log.info("validation fails ;( ");
            }
        }

        filterChain.doFilter(request,response);
    }
}
