package io.chanwook.taglink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * @author chanwook
 */
public class TagLinkMongoRepositoryImpl implements TagLinkMongoRepositoryCustom {

    private final Logger logger = LoggerFactory.getLogger(TagLinkMongoRepositoryImpl.class);

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public void bulkInsertIfNotExist(List<TagLink> tagLinks) {
        // TODO bulk operation..
        tagLinks.stream().forEach(t -> {
            if (!mongoOperations.exists(isUniqueTagLink(t), TagLink.class)) {
                mongoOperations.insert(t);
            }
//            mongoOperations.upsert(isUniqueTagLink(t), updateOfTagLink(t), TagLink.class);
        });
    }

    private Update updateOfTagLink(TagLink t) {
        return Update.update("tag", t.getTag()).set("href", t.getHref()).set("title", t.getTitle()).set("target", t.getTarget());
    }

    private Query isUniqueTagLink(TagLink t) {
        return query(
                where("tag").is(t.getTag())
                        .and("href").is(t.getHref())
                        .and("title").is(t.getTitle())
                        .and("target").is(t.getTarget())
        );
    }

    @Override
    public List<TagLink> findByTag(String[] tags, String currentHref, String profile) {
        final Query query = query(where("tag").in(Arrays.asList(tags)).and("target").is(profile));

        final List<TagLink> result = mongoOperations.find(query, TagLink.class);

        final TagLink[] objects = result.stream()
                .filter(tagLink -> !currentHref.equals(tagLink.getHref()))
                .toArray(TagLink[]::new);

        if (logger.isDebugEnabled()) {
            logger.debug("Find tag - Query result is " + result.size() + ", filtered result is " + objects.length);
        }

        return Arrays.asList(objects);
    }
}
