package uz.behzod.restaurantApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uz.behzod.restaurantApp.errors.BadRequestException;
import uz.behzod.restaurantApp.errors.ConflictException;
import uz.behzod.restaurantApp.errors.NotFoundException;
import uz.behzod.restaurantApp.errors.UnauthorizedException;
import uz.behzod.restaurantApp.util.BeanUtils;

import java.util.function.Supplier;

@Component
@Slf4j
public class BaseService {


    protected static Supplier<NotFoundException> notFoundExceptionThrow(String message) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);

        return () -> new NotFoundException(
                BeanUtils
                        .getBean(LocalizationService.class)
                        .localize("exception.{entity}.not_found", new Object[]{message}, localizationService)
        );
    }

    protected static Supplier<NotFoundException> notFoundExceptionThrow(String entityName, String param, Object value) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () ->
                new NotFoundException(
                        BeanUtils
                                .getBean(LocalizationService.class)
                                .localize("entity.not.found", new Object[]{localizationService.localize(entityName, entityName)})
                );
    }

    protected static Supplier<BadRequestException> badRequestExceptionThrow(String message) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);

        return () -> new BadRequestException(
                BeanUtils
                        .getBean(LocalizationService.class)
                        .localize("exception.bad_request", new Object[]{message}, localizationService)

        );
    }

    protected static Supplier<BadRequestException> badRequestExceptionThrow(String entityName, String param, Object value) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);

        return () -> new BadRequestException(
                BeanUtils
                        .getBean(LocalizationService.class)
                        .localize("exception.bad_request", new Object[]{localizationService.localize(entityName, entityName)})

        );
    }

    protected static Supplier<ConflictException> conflictExceptionThrow(String message) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () -> new ConflictException(
                BeanUtils
                        .getBean(LocalizationService.class)
                        .localize("exception.conflict", new Object[]{message}, localizationService)
        );
    }


    protected static Supplier<ConflictException> conflictExceptionThrow(String entityName, String param, Object value) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () -> new ConflictException(
                BeanUtils
                        .getBean(LocalizationService.class)
                        .localize("exception.conflict", new Object[]{localizationService.localize(entityName, entityName)})
        );
    }

    protected static Supplier<UnauthorizedException> unauthorizedExceptionThrow(String message) {
        LocalizationService localizationService = BeanUtils.getBean(LocalizationService.class);
        return () -> new UnauthorizedException(
                BeanUtils
                        .getBean(LocalizationService.class)
                        .localize("exception.unauthorized", new Object[]{message}, localizationService)
        );
    }
}
