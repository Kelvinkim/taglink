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

    public TagLink() {
    }

    public TagLink(String title, String href, String tag) {
        this.title = title;
        this.href = href;
        this.tag = tag;
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

    public static List<TagLink> create(String[] requestTags, String title, String href) {
        final TagLink[] tagLinks = Arrays.stream(requestTags)
                .map(tag -> new TagLink(title, href, tag))
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
                '}';
    }
}
