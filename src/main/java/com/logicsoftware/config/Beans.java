package com.logicsoftware.config;

import com.logicsoftware.utils.i18n.Messages;
import io.quarkus.vertx.http.runtime.CurrentVertxRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

@ApplicationScoped
public class Beans {

    @Inject
    CurrentVertxRequest request;
    
    @Produces
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Produces
    public Messages messages() {
        return Messages.builder().defaultLang("pt-BR").request(request).build();
    }
}
