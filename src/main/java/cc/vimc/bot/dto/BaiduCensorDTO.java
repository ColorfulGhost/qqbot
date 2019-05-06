package cc.vimc.bot.dto;

import java.util.List;

public class BaiduCensorDTO {

    /**
     * result : {"antiporn":{"result":[{"probability":7.8E-5,"class_name":"性感"},{"probability":1.0E-6,"class_name":"色情"},{"probability":0.999922,"class_name":"正常"}],"conclusion":"正常","log_id":1612587304059199822,"result_fine":[{"probability":0,"class_name":"一般色情"},{"probability":0.998109,"class_name":"一般正常"},{"probability":0,"class_name":"卡通色情"},{"probability":0.001783,"class_name":"卡通正常"},{"probability":2.6E-5,"class_name":"特殊类"},{"probability":7.7E-5,"class_name":"女性性感"},{"probability":1.0E-6,"class_name":"男性性感"},{"probability":0,"class_name":"SM"},{"probability":0,"class_name":"低俗"},{"probability":1.0E-6,"class_name":"性玩具"},{"probability":2.0E-6,"class_name":"亲密行为"}],"result_num":11,"confidence_coefficient":"确定"}}
     */

    private ResultX result;

    public ResultX getResult() {
        return result;
    }

    public void setResult(ResultX result) {
        this.result = result;
    }

    public static class ResultX {
        /**
         * antiporn : {"result":[{"probability":7.8E-5,"class_name":"性感"},{"probability":1.0E-6,"class_name":"色情"},{"probability":0.999922,"class_name":"正常"}],"conclusion":"正常","log_id":1612587304059199822,"result_fine":[{"probability":0,"class_name":"一般色情"},{"probability":0.998109,"class_name":"一般正常"},{"probability":0,"class_name":"卡通色情"},{"probability":0.001783,"class_name":"卡通正常"},{"probability":2.6E-5,"class_name":"特殊类"},{"probability":7.7E-5,"class_name":"女性性感"},{"probability":1.0E-6,"class_name":"男性性感"},{"probability":0,"class_name":"SM"},{"probability":0,"class_name":"低俗"},{"probability":1.0E-6,"class_name":"性玩具"},{"probability":2.0E-6,"class_name":"亲密行为"}],"result_num":11,"confidence_coefficient":"确定"}
         */

        private Antiporn antiporn;

        public Antiporn getAntiporn() {
            return antiporn;
        }

        public void setAntiporn(Antiporn antiporn) {
            this.antiporn = antiporn;
        }

        public static class Antiporn {
            /**
             * result : [{"probability":7.8E-5,"class_name":"性感"},{"probability":1.0E-6,"class_name":"色情"},{"probability":0.999922,"class_name":"正常"}]
             * conclusion : 正常
             * log_id : 1612587304059199822
             * result_fine : [{"probability":0,"class_name":"一般色情"},{"probability":0.998109,"class_name":"一般正常"},{"probability":0,"class_name":"卡通色情"},{"probability":0.001783,"class_name":"卡通正常"},{"probability":2.6E-5,"class_name":"特殊类"},{"probability":7.7E-5,"class_name":"女性性感"},{"probability":1.0E-6,"class_name":"男性性感"},{"probability":0,"class_name":"SM"},{"probability":0,"class_name":"低俗"},{"probability":1.0E-6,"class_name":"性玩具"},{"probability":2.0E-6,"class_name":"亲密行为"}]
             * result_num : 11
             * confidence_coefficient : 确定
             */

            private String conclusion;
            private long log_id;
            private int result_num;
            private String confidence_coefficient;
            private List<Result> result;
            private List<ResultFine> result_fine;

            public String getConclusion() {
                return conclusion;
            }

            public void setConclusion(String conclusion) {
                this.conclusion = conclusion;
            }

            public long getLog_id() {
                return log_id;
            }

            public void setLog_id(long log_id) {
                this.log_id = log_id;
            }

            public int getResult_num() {
                return result_num;
            }

            public void setResult_num(int result_num) {
                this.result_num = result_num;
            }

            public String getConfidence_coefficient() {
                return confidence_coefficient;
            }

            public void setConfidence_coefficient(String confidence_coefficient) {
                this.confidence_coefficient = confidence_coefficient;
            }

            public List<Result> getResult() {
                return result;
            }

            public void setResult(List<Result> result) {
                this.result = result;
            }

            public List<ResultFine> getResult_fine() {
                return result_fine;
            }

            public void setResult_fine(List<ResultFine> result_fine) {
                this.result_fine = result_fine;
            }

            public static class Result {
                /**
                 * probability : 7.8E-5
                 * class_name : 性感
                 */

                private double probability;
                private String class_name;

                public double getProbability() {
                    return probability;
                }

                public void setProbability(double probability) {
                    this.probability = probability;
                }

                public String getClass_name() {
                    return class_name;
                }

                public void setClass_name(String class_name) {
                    this.class_name = class_name;
                }
            }

            public static class ResultFine {
                /**
                 * probability : 0
                 * class_name : 一般色情
                 */

                private int probability;
                private String class_name;

                public int getProbability() {
                    return probability;
                }

                public void setProbability(int probability) {
                    this.probability = probability;
                }

                public String getClass_name() {
                    return class_name;
                }

                public void setClass_name(String class_name) {
                    this.class_name = class_name;
                }
            }
        }
    }
}
