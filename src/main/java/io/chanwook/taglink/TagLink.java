package io.chanwook.taglink;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author chanwook
 */
public class TagLink implements Serializable {

    @Id
    private String id;

    private String href;

    private String title;

    private String tag;

    private String target;

    public TagLink() {
    }

    public TagLink(String title, String href, String tag, String target) {
        this.title = title;
        this.href = href;
        this.tag = tag;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public static List<TagLink> create(String[] requestTags, String title, String href, String profile) {
        final TagLink[] tagLinks = Arrays.stream(requestTags)
                .map(tag -> new TagLink(title, href, tag, profile))
                .toArray(TagLink[]::new);
        return Arrays.asList(tagLinks);
    }

    @Override
    public String toString() {
        return "TagLink{" +
                "id='" + id + '\'' +
                ", href='" + href + '\'' +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", target='" + target + '\'' +
                '}';
    }
}
