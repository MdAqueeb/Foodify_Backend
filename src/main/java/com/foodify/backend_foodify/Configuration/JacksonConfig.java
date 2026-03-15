
// package com.foodify.backend_foodify.Configuration;


// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// // import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.SerializationFeature;
// import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;

// @Configuration
// public class JacksonConfig {
    
//     @Bean
//     public ObjectMapper objectMapper() {
//         // MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//         ObjectMapper objectMapper = new ObjectMapper();
//         objectMapper.registerModule(new Hibernate6Module());
//         objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//         // converter.setObjectMapper(objectMapper);
//         return objectMapper;
//     }
// }