package io.github.cnsukidayo.wword.admin.support;

import io.github.cnsukidayo.wword.core.client.CoreFeignClient;
import io.github.cnsukidayo.wword.model.entity.WordId;

import java.util.Comparator;

/**
 * @author sukidayo
 * @date 2023/9/10 14:12
 */
public class DefaultWordIdComparable implements Comparator<WordId> {

    /**
     * 需要DivideFeignClient来获取比较的数值
     */
    private final CoreFeignClient coreFeignClient;

    public DefaultWordIdComparable(CoreFeignClient coreFeignClient) {
        this.coreFeignClient = coreFeignClient;
    }

    @Override
    public int compare(WordId wordId0, WordId wordId1) {
        // 首先查询出单词的详细信息
        Long countedStructure1 = coreFeignClient.countStructure(wordId0.getId());
        Long countedStructure2 = coreFeignClient.countStructure(wordId1.getId());
        if (!countedStructure1.equals(countedStructure2)) {
            return (int) (countedStructure1 - countedStructure2);
        }
        return (int) (coreFeignClient.countValue(wordId0.getId()) - coreFeignClient.countValue(wordId1.getId()));
    }
}
