package com.logicsoftware.utils.i18n;

import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import lombok.*;
import org.jboss.resteasy.util.LocaleHelper;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Messages {
    
    private static final String DEFAULT_LANG_PATH = "lang/messages";

    private @Getter @Setter String defaultLang;

    private @Getter @Setter CurrentVertxRequest request;

    public String getMessage(String key) {
        return ResourceBundle.getBundle(DEFAULT_LANG_PATH, getLocale()).getString(key);
    }
    
    public String getMessage(String key, Object... params) {
        return String.format(ResourceBundle.getBundle(DEFAULT_LANG_PATH, getLocale()).getString(key), params);
    }

    private Locale getLocale() {
        String language = request.getCurrent().request().headers().get("accept-language");
        Locale locale = LocaleHelper.extractLocale(Objects.isNull(language) ? getDefaultLang() : language);
        return locale;
    }
}
