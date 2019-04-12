package cc.vimc.bot.dto;

import java.util.List;

public class TulingReponseDTO {

    /**
     * intent : {"code":10005,"intentName":"","actionName":"","parameters":{"nearby_place":"酒店"}}
     * results : [{"groupType":1,"resultType":"url","values":{"url":"http://m.elong.com/hotel/0101/nlist/#indate=2016-12-10&outdate=2016-12-11&keywords=%E4%BF%A1%E6%81%AF%E8%B7%AF"}},{"groupType":1,"resultType":"text","values":{"text":"亲，已帮你找到相关酒店信息"}}]
     */

    private Intent intent;
    private List<Results> results;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }

    public static class Intent {
        /**
         * code : 10005
         * intentName :
         * actionName :
         * parameters : {"nearby_place":"酒店"}
         */

        private int code;
        private String intentName;
        private String actionName;
        private Parameters parameters;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getIntentName() {
            return intentName;
        }

        public void setIntentName(String intentName) {
            this.intentName = intentName;
        }

        public String getActionName() {
            return actionName;
        }

        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        public Parameters getParameters() {
            return parameters;
        }

        public void setParameters(Parameters parameters) {
            this.parameters = parameters;
        }

        public static class Parameters {
            /**
             * nearby_place : 酒店
             */

            private String nearby_place;

            public String getNearby_place() {
                return nearby_place;
            }

            public void setNearby_place(String nearby_place) {
                this.nearby_place = nearby_place;
            }
        }
    }

    public static class Results {
        /**
         * groupType : 1
         * resultType : url
         * values : {"url":"http://m.elong.com/hotel/0101/nlist/#indate=2016-12-10&outdate=2016-12-11&keywords=%E4%BF%A1%E6%81%AF%E8%B7%AF"}
         */

        private int groupType;
        private String resultType;
        private Values values;

        public int getGroupType() {
            return groupType;
        }

        public void setGroupType(int groupType) {
            this.groupType = groupType;
        }

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public Values getValues() {
            return values;
        }

        public void setValues(Values values) {
            this.values = values;
        }

        public static class Values {
            /**
             * url : http://m.elong.com/hotel/0101/nlist/#indate=2016-12-10&outdate=2016-12-11&keywords=%E4%BF%A1%E6%81%AF%E8%B7%AF
             */

            private String url;
            private String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }


            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
