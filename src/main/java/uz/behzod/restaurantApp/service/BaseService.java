package uz.behzod.restaurantApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import uz.behzod.restaurantApp.constants.MessageConstants;
import uz.behzod.restaurantApp.errors.BadRequestException;
import uz.behzod.restaurantApp.errors.ConflictException;
import uz.behzod.restaurantApp.errors.NotFoundException;
import uz.behzod.restaurantApp.repository.MenuItemRepository;
import uz.behzod.restaurantApp.repository.OrderItemRepository;
import uz.behzod.restaurantApp.repository.OrderRepository;
import uz.behzod.restaurantApp.util.BeanUtils;
import uz.behzod.restaurantApp.validator.*;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class BaseService implements MessageConstants {
    @Lazy
    @Autowired
    NameValidator nameValidator;
    @Autowired
    CompanyValidator companyValidator;
    @Autowired
    TinValidator tinValidator;
    @Autowired
    PinflValidator pinflValidator;
    @Autowired
    BranchValidator branchValidator;
    @Autowired
    MenuValidator menuValidator;
    @Autowired
    PriceValidator priceValidator;
    @Autowired
    ProductValidator productValidator;
    @Autowired
    UnitValidator unitValidator;
    @Autowired
    MenuItemValidator menuItemValidator;
    //    @Autowired
//    QtyValidator qtyValidator;
    @Autowired
    DepartmentValidator departmentValidator;
    @Autowired
    BarcodeValidator barcodeValidator;
    @Autowired
    UsernameValidator usernameValidator;
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    RoleValidator roleValidator;
    @Autowired
    PositionValidator positionValidator;
    OrderRepository orderRepository;
    MenuItemRepository menuItemRepository;
    OrderItemRepository orderItemRepository;


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


