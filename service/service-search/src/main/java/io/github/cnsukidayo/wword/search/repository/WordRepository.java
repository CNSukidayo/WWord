package io.github.cnsukidayo.wword.search.repository;

import io.github.cnsukidayo.wword.model.entity.es.WordES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author sukidayo
 * @date 2023/9/10 23:00
 */
public interface WordRepository extends ElasticsearchRepository<WordES, Long> {

    Page<WordES> findByLanguageIdAndWordContaining(Long languageId, String word, Pageable pageable);

}
