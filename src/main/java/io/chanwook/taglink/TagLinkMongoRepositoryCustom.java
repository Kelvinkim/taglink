package io.chanwook.taglink;

import java.util.List;

/**
 * @author chanwook
 */
public interface TagLinkMongoRepositoryCustom {

    /**
     * 등록되어 있지 않은 태그 링크라면 저장
     *
     * @param tagLink
     */
    void bulkInsertIfNotExist(List<TagLink> tagLink);

    /**
     * tag에 해당하는 전체 태그링크 조회
     *
     * @param tags
     * @return
     */
    List<TagLink> findByTag(String[] tags);
}
