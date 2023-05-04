package adega.de.vinhos.adegadevinhos.config;

//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
////        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
//                .authorizeHttpRequests((auth) -> auth
//                .anyRequest().authenticated()
//        ).httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user = User.withUsername("devdojo")
//                .password(encoder.encode("balda"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }

}
