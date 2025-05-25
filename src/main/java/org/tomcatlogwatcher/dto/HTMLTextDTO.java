package org.tomcatlogwatcher.dto;

import lombok.Builder;
import lombok.Data;
import org.tomcatlogwatcher.utility.Utils;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class HTMLTextDTO {

    private final static String OPENING_TAG = "<html>";
    private final static String CLOSING_TAG = "</html>";

    private final static String STYLE_CSS_OPEN = "style='";

    private final static String PARA_WITH_STYLE = "<p ";
    private final static String PARA_OPENING_TAG = "<p>";
    private final static String PARA_CLOSING_TAG = "</p>";

    private final static String BREAK_LINE_TAG = "<br>";

    private final static String STYLE_CSS = "font-family: ";
    private final static String COLOR_CSS = "color: ";
    private final static String WEIGHT_CSS = "font-weight: ";
    private final static String SIZE_CSS = "font-size: ";

    private final static String TAG_CLOSING_SIGN = ">";
    private final static String STYLE_CLOSING_SIGN = ";";

    private String heading;
    private String headingColor;
    private String headingFontSize;
    private String headingStyle;
    private String headingWeight;


    private List<String> textLines = new ArrayList<>();
    private String textParagraph;

    private String textColor;
    private String textFontSize;
    private String textStyle;
    private String textWeight;

    private Boolean breakLineOnEveryText;

    @Override
    public String toString(){
        return getGeneratedText();
    }

    public String getGeneratedText(){
        StringBuilder generatedText = new StringBuilder();

        generatedText.append(OPENING_TAG);

        if(Utils.areStringsValid(heading)){
            StringBuilder headingHTML = new StringBuilder();

            String headingDesign = generateStyle(headingStyle, headingWeight, headingFontSize, headingColor);

            if(Utils.areStringsValid(headingDesign)){
                headingHTML.append(PARA_WITH_STYLE).append(headingDesign).append(TAG_CLOSING_SIGN);
            } else {
                headingHTML.append(PARA_OPENING_TAG);
            }
            headingHTML.append(heading).append(PARA_CLOSING_TAG).append(BREAK_LINE_TAG);

            generatedText.append(headingHTML);
        }

        if(Utils.areStringsValid(textParagraph) || Utils.isCollectionNotEmpty(textLines)){
            StringBuilder textParagraphHTML = new StringBuilder();

            String textDesign = generateStyle(textStyle, textWeight, textFontSize, textColor);
            if(Utils.areStringsValid(textDesign)){
                textParagraphHTML.append(PARA_WITH_STYLE).append(textDesign).append(TAG_CLOSING_SIGN);
            } else {
                textParagraphHTML.append(PARA_OPENING_TAG);
            }

            if(Utils.isCollectionNotEmpty(textLines)){
                for(String textLine : textLines){
                    textParagraphHTML.append(textLine);
                    if(breakLineOnEveryText){
                        textParagraphHTML.append(BREAK_LINE_TAG);
                    }
                }
            }

            if(Utils.areStringsValid(textParagraph)){
                textParagraphHTML.append(textParagraph);
            }
            textParagraphHTML.append(PARA_CLOSING_TAG).append(BREAK_LINE_TAG);
            generatedText.append(textParagraphHTML);
        }
        generatedText.append(CLOSING_TAG);
        return generatedText.toString();
    }

    private String generateStyle(String style, String weight, String fontSize, String color) {
        StringBuilder styleCss = null;
        if(Utils.areStringsValid(style) || Utils.areStringsValid(fontSize) || Utils.areStringsValid(color) || Utils.areStringsValid(weight)) {
            styleCss = new StringBuilder(STYLE_CSS_OPEN);
            if (Utils.areStringsValid(style)) {
                styleCss.append(STYLE_CSS).append(style).append(STYLE_CLOSING_SIGN);
            }
            if (Utils.areStringsValid(weight)) {
                styleCss.append(WEIGHT_CSS).append(weight).append(STYLE_CLOSING_SIGN);
            }
            if (Utils.areStringsValid(fontSize)) {
                styleCss.append(SIZE_CSS).append(fontSize).append(STYLE_CLOSING_SIGN);
            }
            if (Utils.areStringsValid(color)) {
                styleCss.append(COLOR_CSS).append(color).append(STYLE_CLOSING_SIGN);
            }
            styleCss.append("'");
        }
        return styleCss == null ? "" : styleCss.toString();
    }
}
