package com.smart.redis;

import java.util.List;

public class ExcelParam {
    String name;
    int width;
    String font;
    String[] headers;
    /**
     * 导出数据的样式
     * 1:String left;
     * 2:String center
     * 3:String right
     * 4 int  right
     * 5:float ###,###.## right
     * 6:number: #.00% 百分比 right
     */
    int[] ds_format;
    /**
     * 每列表格的宽度,默认为256 * 14
     */
    int[] widths;
    List<String[]> data;

    private ExcelParam() {

    }
    //使用Builder模式，设置默认参数和必填参数
    public static class Builder {
        String name;
        int width = 256 * 14;
        String font = "微软雅黑";
        String[] headers;
        int[] ds_format;
        int[] widths;
        List<String[]> data;

        public Builder(String name) {
            this.name = name;
        }

        public Builder font(String font) {
            this.font = font;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder headers(String[] headers) {
            this.headers = headers;
            return this;
        }

        public Builder ds_format(int[] ds_format) {
            this.ds_format = ds_format;
            return this;
        }

        public Builder widths(int[] widths) {
            this.widths = widths;
            return this;
        }

        public Builder data(List<String[]> data) {
            this.data = data;
            return this;
        }

        public ExcelParam build() {
            ExcelParam excelParam = new ExcelParam();
            excelParam.name = this.name;
            excelParam.data = this.data;
            excelParam.widths = this.widths;
            excelParam.ds_format = this.ds_format;
            excelParam.headers = this.headers;
            excelParam.font = this.font;
            excelParam.width = this.width;
            return excelParam;
        }
    }
}
