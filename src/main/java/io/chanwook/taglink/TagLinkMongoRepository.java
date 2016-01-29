package io.chanwook.taglink;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * @author chanwook
 */
@RepositoryRestResource(collectionResourceRel = "tag", path = "tag")
public interface TagLinkMongoRepository extends MongoRepository<TagLink, String>, TagLinkMongoRepositoryCustom {
}
