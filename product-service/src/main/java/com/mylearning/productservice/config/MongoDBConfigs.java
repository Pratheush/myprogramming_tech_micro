package com.mylearning.productservice.config;

/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = { "com.mylearning.productservice.repository" })
//@ComponentScan("com.mylearning.productservice")
@PropertySource("classpath:application.properties")
public class MongoDBConfigs {

    //@Autowired
    private final MongoDatabaseFactory mongoDatabaseFactory;

    //@Autowired
    private final MongoMappingContext mongoMappingContext;

    public MongoDBConfigs(MongoDatabaseFactory mongoDatabaseFactory, MongoMappingContext mongoMappingContext) {
        this.mongoDatabaseFactory = mongoDatabaseFactory;
        this.mongoMappingContext = mongoMappingContext;
    }



    @Bean
    public MappingMongoConverter mappingMongoConverter() {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

}
*/
