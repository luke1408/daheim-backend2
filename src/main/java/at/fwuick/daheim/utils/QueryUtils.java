package at.fwuick.daheim.utils;

public class QueryUtils {
  public static QuerySelect select(String fields, String tableName) {
    return new QuerySelect(fields, tableName);
  }

  public static QueryUpdate update(String tableName) {
    return new QueryUpdate(tableName);
  }

}
