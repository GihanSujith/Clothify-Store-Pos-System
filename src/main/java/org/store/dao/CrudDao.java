package org.store.dao;

import org.store.dto.CustomerDto;

public interface CrudDao <T> extends SuperDao{
    boolean save(T dto);
    boolean delete(String id);
}
