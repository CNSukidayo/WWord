package io.github.cnsukidayo.wword.core.client;

import io.github.cnsukidayo.wword.model.entity.Divide;
import io.github.cnsukidayo.wword.model.entity.Word;
import io.github.cnsukidayo.wword.model.entity.WordId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/10 11:23
 */
@FeignClient("service-core")
public interface CoreFeignClient {
    /**
     * 查询出所有的父划分,并且会按照divideId进行升序
     *
     * @return 返回集合不为空nul
     */
    @GetMapping("/remove/core/divide/listParentDivide")
    List<Divide> listParentDivide();

    /**
     * 根据划分id查询出划分的详细信息
     *
     * @param divideId 划分id不为空
     * @return 返回划分对象, 可能为null
     */
    @GetMapping("/remove/core/divide/selectById")
    Divide selectById(@RequestParam("divideId") Long divideId);

    /**
     * 判断是否存在一个学校
     *
     * @param schoolName 学校名称参数不为空
     * @return 返回是否存在
     */
    @GetMapping("/remote/u/university/has_university")
    Boolean hasUniversity(@RequestParam("school_name") String schoolName);

    /**
     * 查询某个父划分下的有哪些单词
     *
     * @param divideId 父划分的id(所谓父划分就是在divide表中parent_id为-1的划分)
     * @return 返回的集合不为null
     */
    @GetMapping("/remote/u/wordId/selectWordIdByDivideId")
    List<WordId> selectWordIdByDivideId(@RequestParam("divideId") Long divideId);

    /**
     * 查询比当前划分大的并且单词相同的WordId
     *
     * @param wordId wordId必须不为null
     * @return 返回集合不为null
     */
    @PostMapping("/remote/u/wordId/selectSameWordIdWord")
    List<WordId> selectSameWordIdWord(WordId wordId);

    /**
     * 根据单词的id查询单词的详细信息
     *
     * @param wordId 单词id不为null
     * @return 返回的集合不为null
     */
    @GetMapping("/remote/u/wordId/selectWordById")
    List<Word> selectWordById(@RequestParam("wordId") Long wordId);

    /**
     * 添加一个WordId
     *
     * @param wordId wordId不为空
     * @return 返回中不为null并且id会更新
     */
    @PostMapping("/remote/u/wordId/saveWordId")
    WordId saveWordId(@RequestBody WordId wordId);

    /**
     * 添加一个单词
     *
     * @param word 待添加的单词不为null
     * @return 返回插入后的结果不为null
     */
    @PostMapping("/remote/u/wordId/saveWord")
    Word saveWord(@RequestBody Word word);

    /**
     * 查询一个单词的结构数量(方便排序)
     *
     * @param wordId 单词id不为null
     * @return 返回数量不为null
     */
    @GetMapping("/remote/u/wordId/countStructure")
    Long countStructure(@RequestParam Long wordId);

    /**
     * 查询一个单词的信息数
     *
     * @param wordId 单词id不为null
     * @return 返回数量不为null
     */
    @GetMapping("/remote/u/wordId/countValue")
    Long countValue(@RequestParam Long wordId);

    /**
     * 判断一个单词是否在一个划分中
     *
     * @param word     单词内容不为null
     * @param divideId 划分id不为null
     * @return 返回值不为null
     */
    @GetMapping("/remote/u/wordId/exist")
    Boolean exist(@RequestParam("word") String word, @RequestParam("divideId") Long divideId);

}
