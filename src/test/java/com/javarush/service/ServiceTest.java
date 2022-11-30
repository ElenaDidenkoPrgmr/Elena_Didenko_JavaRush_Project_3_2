package com.javarush.service;

import com.javarush.entity.User;
import com.javarush.repository.Repository;
import com.javarush.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private Service<Integer, String> service;

    @Mock
    private Repository<String, User> repository;

    @BeforeEach
    void init(){
        service = new Service<>(repository);
    }

    @Test
    void test_parsingStringToInt_ShouldThrowException_WhenStringIsNull() {
        assertThrows(ServiceException.class,
                () -> service.parsingStringToInt(null,"valueName",LogManager.getLogger(Service.class)));

    }

    @Test
    void test_parsingStringToInt_ShouldThrowException_WhenStringIsNotNumber() {
        assertThrows(ServiceException.class,
                () -> service.parsingStringToInt("value","valueName",LogManager.getLogger(Service.class)));

    }

    @Test
    void test_parsingStringToInt_ShouldThrowException_WhenStringIsNumber() {
        int expected = 5;
        assertEquals(expected, service.parsingStringToInt("5","valueName",LogManager.getLogger(Service.class)));
    }
}
