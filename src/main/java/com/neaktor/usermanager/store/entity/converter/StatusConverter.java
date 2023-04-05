package com.neaktor.usermanager.store.entity.converter;

import com.neaktor.usermanager.store.entity.User;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class StatusConverter implements AttributeConverter<User.Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(User.Status attribute) {
        if (Objects.requireNonNull(attribute) == User.Status.ONLINE) {
            return 1;
        }
        return 0;
    }

    @Override
    public User.Status convertToEntityAttribute(Integer dbData) {
        if (dbData == 1) {
            return User.Status.ONLINE;
        }
        return User.Status.OFFLINE;
    }
}
