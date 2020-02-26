package com.coupon.auth;

import com.coupon.properties.PropertyManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("########## Initiating Custom filter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        System.out.println("CORS FILTER : "+request.getServletPath());

        /*

        String corsAllowedPath[] = PropertyManager.getAllowedCORSOriginPath();
        for (String requestPath : corsAllowedPath) {
            if (request.getServletPath().contains(requestPath)) {
                String origin = PropertyManager.getAllowedCORSOrigin();
                response.addHeader("Access-Control-Allow-Origin", origin);
                response.addHeader("Access-Control-Allow-Methods", "*");
                response.addHeader("Access-Control-Allow-Headers", "*");
            }
        }

        */

        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }


}
