package io.github.cnsukidayo.wword.model.bo;

import com.github.xiaoymin.knife4j.annotations.Ignore;

/**
 * 用于数据清洗的中间转换类,最终会将该BO对象落库到entity实体类
 *
 * @author sukidayo
 * @date 2023/8/1 19:20
 */
@Ignore
public class JsonWordBO {
    /**
     * 单词的序号
     */
    private Long wordRank;

    /**
     * 单词
     */
    private String headWord;

    /**
     * 上下文
     */
    private Content content;

    /**
     * 单词书id
     */
    private String bookId;


    public static class Content {

        /**
         * 单词
         */
        private Word word;


        public static class Word {

            /**
             * 单词头(实际上就是单词内容)
             */
            private String wordHead;

            /**
             * 单词id(书本id+单词的序号)
             */
            private String wordId;

            /**
             * 不知道,反正又是一个不同的content
             */
            private InternalContent content;

            public static class InternalContent {

                /**
                 * 单词相关测试
                 */
                private Exam[] exam;

                /**
                 * 例句相关内容
                 */
                private Sentence sentence;

                /**
                 * 美式音标
                 */
                private String usphone;

                // todo
                /**
                 * 近义词
                 */
                private Syno syno;

                /**
                 * 英式音标
                 */
                private String ukphone;

                /**
                 * 英音发音https请求参数
                 */
                private String ukspeech;

                /**
                 * 短语
                 */
                private Phrase phrase;

                // todo
                /**
                 * 同根词
                 */
                private RelWord relWord;

                /**
                 * 美音发音https请求参数
                 */
                private String usspeech;

                /**
                 * 翻译,注意这是一个数组
                 */
                private Tran[] trans;

                public static class Exam {

                    /**
                     * 问题
                     */
                    private String question;

                    /**
                     * 答案
                     */
                    private Answer answer;

                    /**
                     * 测试类型
                     */
                    private Integer examType;

                    /**
                     * 选择
                     */
                    private Choice[] choices;

                    public static class Answer {

                        /**
                         * 答案的解释
                         */
                        private String explain;

                        /**
                         * 正确的答案索引,哪一个答案是正确的
                         */
                        private Integer rightIndex;

                        public Answer() {
                        }

                        public String getExplain() {
                            return explain;
                        }

                        public void setExplain(String explain) {
                            this.explain = explain;
                        }

                        public Integer getRightIndex() {
                            return rightIndex;
                        }

                        public void setRightIndex(Integer rightIndex) {
                            this.rightIndex = rightIndex;
                        }
                    }

                    public static class Choice {

                        /**
                         * 选项索引
                         */
                        private String choiceIndex;

                        /**
                         * 选项的内容
                         */
                        private String choice;

                        public Choice() {
                        }

                        public String getChoiceIndex() {
                            return choiceIndex;
                        }

                        public void setChoiceIndex(String choiceIndex) {
                            this.choiceIndex = choiceIndex;
                        }

                        public String getChoice() {
                            return choice;
                        }

                        public void setChoice(String choice) {
                            this.choice = choice;
                        }
                    }

                    public Exam() {
                    }

                    public String getQuestion() {
                        return question;
                    }

                    public void setQuestion(String question) {
                        this.question = question;
                    }

                    public Answer getAnswer() {
                        return answer;
                    }

                    public void setAnswer(Answer answer) {
                        this.answer = answer;
                    }

                    public Integer getExamType() {
                        return examType;
                    }

                    public void setExamType(Integer examType) {
                        this.examType = examType;
                    }

                    public Choice[] getChoices() {
                        return choices;
                    }

                    public void setChoices(Choice[] choices) {
                        this.choices = choices;
                    }
                }

                public static class Sentence {

                    /**
                     * 例句数组
                     */
                    private InternalSentence[] sentences;

                    /**
                     * 描述
                     */
                    private String desc;

                    public static class InternalSentence {
                        /**
                         * 例句的英语
                         */
                        private String sContent;

                        /**
                         * 例句的中文翻译
                         */
                        private String sCn;

                        public InternalSentence() {
                        }

                        public String getsContent() {
                            return sContent;
                        }

                        public void setsContent(String sContent) {
                            this.sContent = sContent;
                        }

                        public String getsCn() {
                            return sCn;
                        }

                        public void setsCn(String sCn) {
                            this.sCn = sCn;
                        }
                    }

                    public Sentence() {
                    }

                    public InternalSentence[] getSentences() {
                        return sentences;
                    }

                    public void setSentences(InternalSentence[] sentences) {
                        this.sentences = sentences;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class Syno {
                    /**
                     * 近义词数组
                     */
                    private InternalSyno[] synos;

                    /**
                     * 描述
                     */
                    private String desc;

                    /**
                     * 近义词
                     */
                    public static class InternalSyno {
                        /**
                         * 词性
                         */
                        private String pos;

                        /**
                         * 对应词义
                         */
                        private String tran;

                        /**
                         * 近义词/词组
                         */
                        private HWD[] hwds;

                        public static class HWD {
                            /**
                             * 内容
                             */
                            private String w;

                            public HWD() {
                            }

                            public String getW() {
                                return w;
                            }

                            public void setW(String w) {
                                this.w = w;
                            }
                        }

                        public InternalSyno() {
                        }

                        public String getPos() {
                            return pos;
                        }

                        public void setPos(String pos) {
                            this.pos = pos;
                        }

                        public String getTran() {
                            return tran;
                        }

                        public void setTran(String tran) {
                            this.tran = tran;
                        }

                        public HWD[] getHwds() {
                            return hwds;
                        }

                        public void setHwds(HWD[] hwds) {
                            this.hwds = hwds;
                        }
                    }

                    public Syno() {
                    }

                    public InternalSyno[] getSynos() {
                        return synos;
                    }

                    public void setSynos(InternalSyno[] synos) {
                        this.synos = synos;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class Phrase {
                    /**
                     * 短语数组
                     */
                    private InternalPhrase[] phrases;

                    /**
                     * 描述
                     */
                    private String desc;

                    public static class InternalPhrase {
                        /**
                         * 短语的英文
                         */
                        private String pContent;

                        /**
                         * 短语英文的翻译
                         */
                        private String pCn;

                        public InternalPhrase() {
                        }

                        public String getpContent() {
                            return pContent;
                        }

                        public void setpContent(String pContent) {
                            this.pContent = pContent;
                        }

                        public String getpCn() {
                            return pCn;
                        }

                        public void setpCn(String pCn) {
                            this.pCn = pCn;
                        }
                    }

                    public Phrase() {
                    }

                    public InternalPhrase[] getPhrases() {
                        return phrases;
                    }

                    public void setPhrases(InternalPhrase[] phrases) {
                        this.phrases = phrases;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class RelWord {
                    /**
                     * 同根词
                     */
                    private InternalRelWord[] rels;

                    /**
                     * 描述
                     */
                    private String desc;

                    public static class InternalRelWord {
                        /**
                         * 同根词的词性
                         */
                        private String pos;

                        /**
                         * 同根词单词
                         */
                        private InternalWord[] words;

                        public static class InternalWord {
                            /**
                             * 同根词英文
                             */
                            private String hwd;

                            /**
                             * 同根词翻译
                             */
                            private String tran;

                            public InternalWord() {
                            }

                            public String getHwd() {
                                return hwd;
                            }

                            public void setHwd(String hwd) {
                                this.hwd = hwd;
                            }

                            public String getTran() {
                                return tran;
                            }

                            public void setTran(String tran) {
                                this.tran = tran;
                            }
                        }

                        public InternalRelWord() {
                        }

                        public String getPos() {
                            return pos;
                        }

                        public void setPos(String pos) {
                            this.pos = pos;
                        }

                        public InternalWord[] getWords() {
                            return words;
                        }

                        public void setWords(InternalWord[] words) {
                            this.words = words;
                        }
                    }

                    public RelWord() {
                    }

                    public InternalRelWord[] getRels() {
                        return rels;
                    }

                    public void setRels(InternalRelWord[] rels) {
                        this.rels = rels;
                    }

                    public String getDesc() {
                        return desc;
                    }

                    public void setDesc(String desc) {
                        this.desc = desc;
                    }
                }

                public static class Tran {

                    /**
                     * 单词的词性
                     */
                    private String pos;

                    /**
                     * 单词的中文译文
                     */
                    private String tranCn;

                    /**
                     * 中译描述
                     */
                    private String descCn;

                    /**
                     * 英译(也就是英文字典用英文对该次的解释),就像中文字典对一个中文进行翻译的时候使用的也是中文.
                     */
                    private String tranOther;

                    /**
                     * 英译描述,实际上这里的字段是other.便可以扩展一下,如果是中日互译.则这里tranOther就是日语.<br>
                     * 而descOther字段的值便是日译.
                     */
                    private String descOther;

                    public Tran() {
                    }

                    public String getPos() {
                        return pos;
                    }

                    public void setPos(String pos) {
                        this.pos = pos;
                    }

                    public String getTranCn() {
                        return tranCn;
                    }

                    public void setTranCn(String tranCn) {
                        this.tranCn = tranCn;
                    }

                    public String getDescCn() {
                        return descCn;
                    }

                    public void setDescCn(String descCn) {
                        this.descCn = descCn;
                    }

                    public String getTranOther() {
                        return tranOther;
                    }

                    public void setTranOther(String tranOther) {
                        this.tranOther = tranOther;
                    }

                    public String getDescOther() {
                        return descOther;
                    }

                    public void setDescOther(String descOther) {
                        this.descOther = descOther;
                    }
                }

                public InternalContent() {
                }

                public Exam[] getExam() {
                    return exam;
                }

                public void setExam(Exam[] exam) {
                    this.exam = exam;
                }

                public Sentence getSentence() {
                    return sentence;
                }

                public void setSentence(Sentence sentence) {
                    this.sentence = sentence;
                }

                public String getUsphone() {
                    return usphone;
                }

                public void setUsphone(String usphone) {
                    this.usphone = usphone;
                }

                public Syno getSyno() {
                    return syno;
                }

                public void setSyno(Syno syno) {
                    this.syno = syno;
                }

                public String getUkphone() {
                    return ukphone;
                }

                public void setUkphone(String ukphone) {
                    this.ukphone = ukphone;
                }

                public String getUkspeech() {
                    return ukspeech;
                }

                public void setUkspeech(String ukspeech) {
                    this.ukspeech = ukspeech;
                }

                public Phrase getPhrase() {
                    return phrase;
                }

                public void setPhrase(Phrase phrase) {
                    this.phrase = phrase;
                }

                public RelWord getRelWord() {
                    return relWord;
                }

                public void setRelWord(RelWord relWord) {
                    this.relWord = relWord;
                }

                public String getUsspeech() {
                    return usspeech;
                }

                public void setUsspeech(String usspeech) {
                    this.usspeech = usspeech;
                }

                public Tran[] getTrans() {
                    return trans;
                }

                public void setTrans(Tran[] trans) {
                    this.trans = trans;
                }
            }

            public Word() {
            }

            public String getWordHead() {
                return wordHead;
            }

            public void setWordHead(String wordHead) {
                this.wordHead = wordHead;
            }

            public String getWordId() {
                return wordId;
            }

            public void setWordId(String wordId) {
                this.wordId = wordId;
            }

            public InternalContent getContent() {
                return content;
            }

            public void setContent(InternalContent content) {
                this.content = content;
            }
        }

        public Content() {
        }

        public Word getWord() {
            return word;
        }

        public void setWord(Word word) {
            this.word = word;
        }
    }

    public JsonWordBO() {
    }

    public Long getWordRank() {
        return wordRank;
    }

    public void setWordRank(Long wordRank) {
        this.wordRank = wordRank;
    }

    public String getHeadWord() {
        return headWord;
    }

    public void setHeadWord(String headWord) {
        this.headWord = headWord;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
