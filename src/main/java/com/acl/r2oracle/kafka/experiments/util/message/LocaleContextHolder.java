package com.acl.r2oracle.kafka.experiments.util.message;

import java.util.Locale;

public class LocaleContextHolder {
    public static final Locale DEFAULT = Locale.ENGLISH;
    private static final String[] SUPPORTED_LANGUAGUES = new String[]{"es", "en"};

    static {
        org.springframework.context.i18n.LocaleContextHolder.setDefaultLocale(DEFAULT);
    }

    private LocaleContextHolder() {
        throw new AssertionError("No 'LocaleContextHolder' instances for you!");
    }

    /**
     * Returns the locale that was set for the current context/thread.
     *
     * @return The available locale for the current context/thread.
     */
    static public Locale getLocale() {
        return org.springframework.context.i18n.LocaleContextHolder.getLocale();
    }

    /**
     * Checks whether the given language is supported by the system's locale context.
     *
     * @param language The language to be checked.
     * @return <b>True</b> if and only if the specified language is supported, <b>false</b> otherwise.
     */
    static public boolean isSupported(String language) {
        return SUPPORTED_LANGUAGUES[0].equals(language) || SUPPORTED_LANGUAGUES[1].equals(language);
    }

    /**
     * Checks whether the given locale is supported by the system's locale context.
     *
     * @param locale The locale to be checked.
     * @return <b>True</b> if and only if the specified locale is supported, <b>false</b> otherwise.
     */
    static public boolean isSupported(Locale locale) {
        return null != locale && isSupported(locale.getLanguage());
    }
}
