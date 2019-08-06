package model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseModel implements Serializable {
    public abstract Object resultConverter(ResultSet result) throws SQLException;
}
