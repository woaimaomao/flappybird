package com.example.yongle.du.tools;

import java.io.InputStream;
import java.util.List;

public interface BaseParser {

    public List<ImageInfo> parse(InputStream is) throws Exception;
    
    public String serialize(List<ImageInfo> imageInfos)throws Exception;
}
