package org.store.dao;

import javafx.collections.ObservableList;
import org.store.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDao <T> extends SuperDao{
    boolean save(T dto);
    boolean delete(String id);
}
