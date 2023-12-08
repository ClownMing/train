package com.ming.train.common.config;

/**
 * @author clownMing
 * 解决前后端交互Long类型精度丢失问题
 */
//@Configuration
//public class jacksonConfig {
//
//    @Bean
//    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
//        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
//        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        objectMapper.registerModule(simpleModule);
//        return objectMapper;
//    }
//
//}
