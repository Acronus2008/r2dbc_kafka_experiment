package com.acl.r2oracle.kafka.experiments.util.message;

import com.acl.r2oracle.kafka.experiments.util.FileUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.regex.Pattern.compile;

public class MessageContextHolder {
    private static final MessageSource SOURCE;

    static {
        SOURCE = new ResourceBundleMessageSource();// (1)
        ((ResourceBundleMessageSource) SOURCE).setBasenames(getPropertiesFilesPaths());
        ((ResourceBundleMessageSource) SOURCE).setDefaultEncoding("UTF-8");
    }

    private MessageContextHolder() {
        throw new AssertionError("No 'MessageContextHolder' instances for you!");
    }

    static public String msg(String code) {
        return SOURCE.getMessage(code, null, LocaleContextHolder.getLocale());
    }

    static public String msg(String code, Object... args) {
        return SOURCE.getMessage(code, 0 != args.length ? args : null, LocaleContextHolder.getLocale());
    }

    static public MessageSource source() {
        return SOURCE;
    }

    private static String[] getPropertiesFilesPaths() {
        Pattern pattern = compile("\\p{javaLetter}+([_]\\p{javaLetter}+)+.properties{1}");
        Function<String, String> mappingFunc = line -> line.endsWith(".properties") ? line.substring(0, line.indexOf("essages_") + 7) : line;
        Stream<String> defaultMessages = FileUtils.findFilesAsStream("classpath*:i18n/*.properties", 2, ".", pattern, mappingFunc);
        return defaultMessages.toArray(String[]::new);
    }
}
