package at.fwuick.daheim.utils;

public class QueryUpdate {

  private String tableName;

  public QueryUpdate(String tableName) {
    this.tableName = tableName;
  }

  public QueryUpdateSet set(String field) {
    return new QueryUpdateSet(this.tableName, field);
  }

}
