package at.fwuick.daheim.utils;

public class QueryUpdateSet {

  private String tableName;
  private String setField;

  public QueryUpdateSet(String tableName, String field) {
    this.tableName = tableName;
    this.setField = field;
  }

  public String where(String field) {
    return String.format("update %s set %s = ? where %s = ?", tableName, setField, field);
  }

}
