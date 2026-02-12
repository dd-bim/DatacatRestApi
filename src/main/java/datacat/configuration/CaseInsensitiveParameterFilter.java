package datacat.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Enumeration;

@Component
public class CaseInsensitiveParameterFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest wrapped = new CaseInsensitiveRequestWrapper(request);
        filterChain.doFilter(wrapped, response);
    }

    private static class CaseInsensitiveRequestWrapper extends HttpServletRequestWrapper {
        private final Map<String, String[]> lowerCaseParamMap;

        CaseInsensitiveRequestWrapper(HttpServletRequest request) {
            super(request);
            Map<String, String[]> original = request.getParameterMap();
            Map<String, String[]> tmp = new LinkedHashMap<>();
            for (Map.Entry<String, String[]> e : original.entrySet()) {
                String key = e.getKey();
                if (key == null) continue;
                String keyLower = key.toLowerCase(Locale.ROOT);
                String[] values = e.getValue();
                if (tmp.containsKey(keyLower)) {
                    tmp.put(keyLower, mergeArrays(tmp.get(keyLower), values));
                } else {
                    tmp.put(keyLower, values);
                }
            }
            lowerCaseParamMap = Collections.unmodifiableMap(tmp);
        }

        private static String[] mergeArrays(String[] a, String[] b) {
            String[] merged = Arrays.copyOf(a, a.length + b.length);
            System.arraycopy(b, 0, merged, a.length, b.length);
            return merged;
        }

        @Override
        public String getParameter(String name) {
            if (name == null) return null;
            String[] vals = lowerCaseParamMap.get(name.toLowerCase(Locale.ROOT));
            return vals != null && vals.length > 0 ? vals[0] : null;
        }

        @Override
        public String[] getParameterValues(String name) {
            if (name == null) return null;
            return lowerCaseParamMap.get(name.toLowerCase(Locale.ROOT));
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(lowerCaseParamMap.keySet());
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return lowerCaseParamMap;
        }
    }
}
