package com.javarush.eldidenko.repository;

import com.javarush.eldidenko.entity.Dialog;
import java.util.Map;

public class DialogRepository extends Repository<Integer, Dialog> {
    public DialogRepository(Map<Integer, Dialog> repository) {
        super(repository);
    }
}
