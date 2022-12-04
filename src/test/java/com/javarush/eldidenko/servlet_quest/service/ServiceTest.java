package com.javarush.eldidenko.servlet_quest.service;

import com.javarush.eldidenko.servlet_quest.entity.User;
import com.javarush.eldidenko.servlet_quest.repository.Repository;
import com.javarush.eldidenko.servlet_quest.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private Service<String, User> service;

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
