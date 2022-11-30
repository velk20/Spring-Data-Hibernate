package softuni.exam.instagraphlite.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Gson createGson() {
        return new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(ctx->LocalDate.parse(ctx.getSource(),DateTimeFormatter.ofPattern("dd/MM/yyyy")),String.class,LocalDate.class);
        return modelMapper;
    }
}
