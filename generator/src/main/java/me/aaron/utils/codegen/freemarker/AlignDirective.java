package me.aaron.utils.codegen.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName AlignDirective
 * @Description Freemarker自定义指令：对齐工具
 * @Author wuhengzhen
 * @Date 2019-11-20 13:12
 */
public class AlignDirective implements TemplateDirectiveModel {
    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        int left = DirectiveUtil.getInt(map, "left");

        if (templateDirectiveBody == null) {
            //do nothing
            return;
        }

        StringWriter writer = new StringWriter();
        templateDirectiveBody.render(writer);
        String results = this.format(writer.toString(), left);
        if (results != null) {
            environment.getOut().write(results);
        }
    }

    /**
     * 格式化缩进
     *
     * @param source 源,可以是多行的文本
     * @param align  左对齐空格数量。
     * @return 将所有文本按首行对齐
     * @throws IOException
     */
    public String format(String source, int align) throws IOException {
        if (source == null) {
            return source;
        }

        String blanks = getBlanks(align);

        StringReader stringReader = new StringReader(source);
        BufferedReader reader = new BufferedReader(stringReader);

        StringBuffer result = new StringBuffer();

        int lineNum = 0;
        String lineStr = null;
        int firstLineBlankCount = 0;
        while ((lineStr = reader.readLine()) != null) {
            lineNum++;

            int blankCount = getLeftBlanksCount(lineStr);

            // 取第一行的空格数
            if (lineNum == 1) {
                firstLineBlankCount = blankCount;
            }

            // 去除空格
            int trimCount = firstLineBlankCount > blankCount ? blankCount : firstLineBlankCount;
            lineStr = lineStr.substring(trimCount, lineStr.length());

            // 对齐
            result.append(blanks);
            result.append(lineStr);
            result.append("\n");

        }
        return result.toString();
    }

    public int getLeftBlanksCount(String str) {
        int index = 0;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ' ' || c == '\t') {
                index++;
            } else {
                break;
            }
        }
        return index;
    }

    public String getBlanks(int count) {
        StringBuffer blanks = new StringBuffer();
        for (int i = 0; i < count; i++) {
            blanks.append(" ");
        }
        return blanks.toString();
    }
}
