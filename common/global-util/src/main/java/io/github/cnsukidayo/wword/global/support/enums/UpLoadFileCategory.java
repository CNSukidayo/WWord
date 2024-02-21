package io.github.cnsukidayo.wword.global.support.enums;

/**
 * @author sukidayo
 * @date 2024/2/20 11:30
 */
public enum UpLoadFileCategory {

    POST("post");
    private final String basePath;

    UpLoadFileCategory(String basePath) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return basePath;
    }
}
