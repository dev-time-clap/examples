package de.devtime.examples.library.persistence.entity;

import java.time.LocalDate;

public class AuthorEntityTestData extends AuthorEntityTestDataBuilder<AuthorEntityTestData> {

  public static AuthorEntityTestData create() {
    return new AuthorEntityTestData();
  }

  public AuthorEntityTestData authorStephenKing() {
    withFirstName("Stephen");
    withLastName("King");
    withArtistName("Stephen King");
    withBirthday(LocalDate.of(1947, 9, 21));
    return and();
  }

  public AuthorEntityTestData authorGeorgeOrwell() {
    withFirstName("Eric Arthur");
    withLastName("Blair");
    withArtistName("George Orwell");
    withBirthday(LocalDate.of(1903, 6, 25));
    return and();
  }

  public AuthorEntityTestData anonymousAuthor() {
    withArtistName("Anonymus");
    return and();
  }
}