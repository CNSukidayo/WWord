package io.github.cnsukidayo.wword.search.service.impl;

import io.github.cnsukidayo.wword.model.base.InputConverter;
import io.github.cnsukidayo.wword.model.entity.es.WordES;
import io.github.cnsukidayo.wword.model.param.AddWordESParam;
import io.github.cnsukidayo.wword.model.params.SearchWordParam;
import io.github.cnsukidayo.wword.search.repository.WordRepository;
import io.github.cnsukidayo.wword.search.service.WordESService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author sukidayo
 * @date 2023/9/11 8:57
 */
@Service
public class WordESServiceImpl implements WordESService {

    private final WordRepository wordRepository;

    public WordESServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public void save(AddWordESParam addWordESParam) {
        Assert.notNull(addWordESParam, "addWordESParam must not be null");

        WordES wordES = addWordESParam.convertTo();

        wordRepository.save(wordES);

    }

    @Override
    public void saveBatch(List<AddWordESParam> addWordESParams) {
        Assert.notNull(addWordESParams, "addWordESParams must not be null");
        List<WordES> collect = addWordESParams.stream()
            .map(InputConverter::convertTo)
            .toList();
        wordRepository.saveAll(collect);
    }

    @Override
    public Page<WordES> searchWord(SearchWordParam searchWordParam) {
        Assert.notNull(searchWordParam, "searchWordParam must not be null");
        Pageable pageable = PageRequest.of(searchWordParam.getCurrent() - 1, searchWordParam.getSize());
        return wordRepository.findByLanguageIdAndWordContaining(searchWordParam.getLanguageId(),
            searchWordParam.getWord(), pageable);
    }

    @Override
    public void removeLanguage(Long languageId) {
        Assert.notNull(languageId, "languageId must not be null");
        wordRepository.removeByLanguageId(languageId);
    }


}
