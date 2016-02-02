package io.chanwook.taglink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * @author chanwook
 */
@RepositoryRestController
public class TagLinkResourceController {

    private final Logger logger = LoggerFactory.getLogger(TagLinkResourceController.class);

    @Autowired
    TagLinkMongoRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/tag/search")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam("tags") String requestTags,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "href", required = false, defaultValue = "") String href) {

        if (logger.isDebugEnabled()) {
            logger.debug("> Receive Request:: " + requestTags + " :: " + title + " :: " + href);
        }

        String[] tags = spiltTag(requestTags);
        if (isEnoughParam(title, href)) {
            if (logger.isDebugEnabled()) {
                logger.debug("> Create TagLink :: " + Arrays.toString(tags));
            }
            repository.bulkInsertIfNotExist(TagLink.create(tags, title, href));
        }

        List<TagLink> tagLinks = repository.findByTag(tags, href);

        if (logger.isDebugEnabled()) {
            logger.debug("> Load tag (" + tagLinks.size() + ") :: " + tagLinks);
        }

        final Resources<TagLink> resources = new Resources<>(tagLinks);
        return ResponseEntity.ok(resources);
    }

    private String[] spiltTag(String requestTags) {
        final String[] tags = requestTags.split(",");
        return Arrays.stream(tags)
                .filter(t -> StringUtils.hasText(t))
                .map(t -> StringUtils.trimWhitespace(t))
                .toArray(String[]::new);
    }

    private boolean isEnoughParam(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "href", required = false) String href) {
        return StringUtils.hasText(title) && StringUtils.hasText(href);
    }
}
