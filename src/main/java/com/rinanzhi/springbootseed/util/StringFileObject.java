package com.rinanzhi.springbootseed.util;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class StringFileObject extends SimpleJavaFileObject {

    private String content;

    public StringFileObject(String name, String content) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = content;
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }
}
