package io.chanwook.taglink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author chanwook
 */
@RepositoryRestController
public class TagLinkResourceController {

    @Autowired
    TagLinkMongoRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/tag/search")
    @ResponseBody
    public ResponseEntity<?> search(@RequestParam("tags") String[] requestTags,
                                    @RequestParam(value = "title", required = false) String title,
                                    @RequestParam(value = "href", required = false) String href) {

        if (isEnoughParam(title, href)) {
            repository.bulkInsertIfNotExist(TagLink.create(requestTags, title, href));
        }

        List<TagLink> tagLinks = repository.findByTag(requestTags);

        final Resources<TagLink> resources = new Resources<>(tagLinks);
        return ResponseEntity.ok(resources);
    }

    private boolean isEnoughParam(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "href", required = false) String href) {
        return StringUtils.hasText(title) && StringUtils.hasText(href);
    }
}
