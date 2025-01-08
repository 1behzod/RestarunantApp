package uz.behzod.restaurantApp.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.constants.MessageConstants;
import uz.behzod.restaurantApp.errors.BadRequestException;
import uz.behzod.restaurantApp.errors.ConflictException;
import uz.behzod.restaurantApp.errors.NotFoundException;
import uz.behzod.restaurantApp.errors.UnauthorizedException;
import uz.behzod.restaurantApp.util.BeanUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Component
@Slf4j
public class BaseService implements MessageConstants {


    protected static Supplier<NotFoundException> notFoundExceptionThrow(String code) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () -> new NotFoundException(localizationService.localize(code));

    }

    protected static Supplier<NotFoundException> notFoundExceptionThrow(String code, String... params) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        List<String> localizedParams = Stream.of(params).map(localizationService::localize).toList();
        return () -> new NotFoundException(localizationService.localize(code, localizedParams.toArray()));
    }

    protected static Supplier<BadRequestException> badRequestExceptionThrow(String code) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () -> new BadRequestException(localizationService.localize(code));
    }

    protected static Supplier<BadRequestException> badRequestExceptionThrow(String code, String... params) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        List<String> localizedParams = Stream.of(params).map(localizationService::localize).toList();
        return () -> new BadRequestException(localizationService.localize(code, localizedParams.toArray()));
    }

    protected static Supplier<ConflictException> conflictExceptionThrow(String code) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () -> new ConflictException(localizationService.localize(code));
    }


    protected static Supplier<ConflictException> conflictExceptionThrow(String code, String... params) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        List<String> localizedParams = Stream.of(params).map(localizationService::localize).toList();
        return () -> new ConflictException(localizationService.localize(code, localizedParams.toArray()));

    }

}


