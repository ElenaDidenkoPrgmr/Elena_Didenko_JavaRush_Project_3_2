package com.javarush.repository;

import com.javarush.entity.Dialog;
import java.util.Map;

public class DialogRepository extends Repository<Integer, Dialog> {
    public DialogRepository(Map<Integer, Dialog> repository) {
        super(repository);
    }
}
