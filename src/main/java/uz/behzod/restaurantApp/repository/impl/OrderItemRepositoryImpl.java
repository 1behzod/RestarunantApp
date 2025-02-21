package uz.behzod.restaurantApp.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import uz.behzod.restaurantApp.repository.custom.OrderItemRepositoryCustom;
import uz.behzod.restaurantApp.repository.custom.OrderRepositoryCustom;


public class OrderItemRepositoryImpl implements OrderItemRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;


}
