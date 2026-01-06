package de.devtime.test;

public enum DbTemplateType {
  EMPTY("template_empty"),
  BOOKS_1000("template_books_1000");

  private final String dbName;

  DbTemplateType(final String dbName) {
    this.dbName = dbName;
  }

  public String getDbName() {
    return this.dbName;
  }
}
