package datacat.configuration;

// =====================================================================================================================
// I M P O R T   S E C T I O N
// =====================================================================================================================
// Spring Boot
import org.springframework.stereotype.Component;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

// Java Servlet
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Java
import java.io.IOException;

// Logging
import lombok.extern.slf4j.Slf4j;

// =====================================================================================================================
// S I M P L E   C O R S   F I L T E R   S E C T I O N
// =====================================================================================================================
/**
 * Einfacher CORS-Filter der garantiert die Header setzt
 * 
 * Dieser Filter wird vor allen anderen Filtern ausgeführt und setzt
 * die CORS-Header direkt auf jede HTTP-Response.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class SimpleCorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        // Setze CORS-Header auf jede Response
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Max-Age", "3600");
        
        log.debug("CORS-Header gesetzt für Request: {} {}", request.getMethod(), request.getRequestURI());
        
        // Bei OPTIONS-Request direkt antworten (Preflight)
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            log.debug("OPTIONS-Request bearbeitet für: {}", request.getRequestURI());
            return;
        }
        
        // Weiter zur nächsten Filter/Controller
        chain.doFilter(req, res);
    }
}