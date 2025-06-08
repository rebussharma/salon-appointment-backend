@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Value(security.api.bearer.token)
    private String apiToken;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/public/**").permitAll() // I might want to let /public calls go through, especially for debugginh
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(new TokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    public class TokenFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain filterChain) throws ServletException, IOException {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = authHeader.substring(7);
            if (!STATIC_TOKEN.equals(token)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Allow request to proceed
            filterChain.doFilter(request, response);
        }
    }
}
