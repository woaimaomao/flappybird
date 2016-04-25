package com.example.yongle.du.tools;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

public class ParseTexture implements BaseParser {

    /**
     * 解析XML方法
     * @param is xml文件的输入流
     * @return List<{@link ImageInfo}> 返回解析后的ImageInfo集合
     */
    
    @Override
    public List<ImageInfo> parse(InputStream is) throws Exception {

        List<ImageInfo> imageInfos = null;
        ImageInfo imageInfo = null;

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "utf-8");

        int eventType = parser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
            case XmlPullParser.START_DOCUMENT:

                imageInfos = new ArrayList<ImageInfo>();
                break;
            case XmlPullParser.START_TAG:
                if( "textureregion".equals(parser.getName())){
                    imageInfo = new ImageInfo();
                    imageInfo.setId(Integer.parseInt(parser.getAttributeValue("", "id")));
                    imageInfo.setName(parser.getAttributeValue("", "src"));
                    imageInfo.setX(Integer.parseInt(parser.getAttributeValue("", "x")));
                    imageInfo.setY(Integer.parseInt(parser.getAttributeValue("", "y")));
                    imageInfo.setWidth(Integer.parseInt(parser.getAttributeValue("", "width")));
                    imageInfo.setHeight(Integer.parseInt(parser.getAttributeValue("", "height")));
                }
                break;
            case XmlPullParser.END_TAG:
                imageInfos.add(imageInfo);
                break;

            default:
                break;
            }
            eventType = parser.next();

        }

        return imageInfos;
    }

    @Override
    public String serialize(List<ImageInfo> imageInfos) throws Exception {
        
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        serializer.setOutput(writer);
        
        serializer.startDocument("utf-8", true);
        serializer.startTag("", "texture");
        
        for(ImageInfo info : imageInfos){
            serializer.startTag("", "textureregion");
            serializer.attribute("", "id", String.valueOf(info.getId()));
            serializer.attribute("", "src", info.getName());
            serializer.attribute("", "x", String.valueOf(info.getX()));
            serializer.attribute("", "y", String.valueOf(info.getY()));
            serializer.attribute("", "width", String.valueOf(info.getWidth()));
            serializer.attribute("", "height", String.valueOf(info.getHeight()));
            serializer.endTag("", "textureregion");
        }
        serializer.endTag("", "texture");
        serializer.endDocument();
        return null;
    }

}




















