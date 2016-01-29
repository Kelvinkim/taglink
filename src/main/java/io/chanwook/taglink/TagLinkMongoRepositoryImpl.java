package io.chanwook.taglink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author chanwook
 */
public class TagLinkMongoRepositoryImpl implements TagLinkMongoRepositoryCustom {

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public void bulkInsertIfNotExist(List<TagLink> tagLinks) {
        // TODO bulk operation..
        tagLinks.stream().forEach(t -> mongoOperations.upsert(isUniqueTagLink(t), updateOfTagLink(t), TagLink.class));
    }

    private Update updateOfTagLink(TagLink t) {
        return Update.update("tag", t.getTag()).set("href", t.getHref()).set("title", t.getTitle());
    }

    private Query isUniqueTagLink(TagLink t) {
        return query(where("tag").is(t.getTag()).and("href").is(t.getHref()).and("title").is(t.getTitle()));
    }

    @Override
    public List<TagLink> findByTag(String[] tags) {
        final List<TagLink> result = mongoOperations.find(query(where("tag").in(tags)), TagLink.class);
        return result;
    }
}
