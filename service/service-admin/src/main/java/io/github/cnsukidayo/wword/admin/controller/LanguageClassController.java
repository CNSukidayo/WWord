package io.github.cnsukidayo.wword.admin.controller;

import io.github.cnsukidayo.wword.admin.service.LanguageClassService;
import io.github.cnsukidayo.wword.model.params.AddLanguageClassParam;
import io.github.cnsukidayo.wword.model.params.UpdateLanguageClassParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

/**
 * @author sukidayo
 * @date 2023/7/29 10:15
 */
@Tag(name = "语种管理接口")
@RestController
@RequestMapping("/api/admin/language_class")
public class LanguageClassController {

    private final LanguageClassService languageClassService;

    public LanguageClassController(LanguageClassService languageClassService) {
        this.languageClassService = languageClassService;
    }

    @Operation(summary = "添加一个语种")
    @PostMapping("save")
    public void save(@RequestBody @Valid AddLanguageClassParam addLanguageClassParam) {
        languageClassService.save(addLanguageClassParam);
    }

    @Operation(summary = "删除一个语种")
    @PostMapping("remove")
    public void remove(@Parameter(description = "语种的id") @RequestParam("id") Long id) {
        languageClassService.removeById(id);
    }

    @Operation(summary = "更新语种信息")
    @PostMapping("update")
    public void update(@RequestBody @Valid UpdateLanguageClassParam updateLanguageClassParam) {
        languageClassService.update(updateLanguageClassParam);
    }


}
