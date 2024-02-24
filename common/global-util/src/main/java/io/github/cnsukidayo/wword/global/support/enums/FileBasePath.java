package io.github.cnsukidayo.wword.global.support.enums;

/**
 * @author sukidayo
 * @date 2024/2/20 11:29
 */
public class FileBasePath {

    public interface BasePathInterface {
        String getBasePath();
    }

    public enum FileNameSpace implements BasePathInterface {
        USER("user"),
        PUBLIC("public");

        private final String basePath;

        FileNameSpace(String basePath) {
            this.basePath = basePath;
        }

        @Override
        public String getBasePath() {
            return basePath;
        }
    }

    public enum FileCategory implements BasePathInterface {
        POST("post"), COVER("cover");
        private final String basePath;

        FileCategory(String basePath) {
            this.basePath = basePath;
        }

        @Override
        public String getBasePath() {
            return basePath;
        }
    }


    public enum FileBasePathDIR implements BasePathInterface {
        UPLOAD_DIR("uploadDir"),
        WORK_DIR("workDir"),
        PUBLIC_DIR("public");
        private final String basePath;

        FileBasePathDIR(String basePath) {
            this.basePath = basePath;
        }

        @Override
        public String getBasePath() {
            return basePath;
        }
    }

}
