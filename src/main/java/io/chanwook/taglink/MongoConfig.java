package io.chanwook.taglink;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

/**
 * @author chanwook
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = {App.class})
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.port}")
    private Integer port;

    @Value("${spring.data.mongodb.username}")
    private String userName;

    @Value("${spring.data.mongodb.database}")
    private String database;

    @Value("${spring.data.mongodb.password}")
    private String password;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        final MongoCredential credential =
                MongoCredential.createCredential(userName, database, password.toCharArray());

        return new MongoClient(singletonList(new ServerAddress(host, port)),
                singletonList(credential));
    }
}
