ALTER TABLE Users
DROP COLUMN Objects;

ALTER TABLE Users
ADD COLUMN createdBY VARCHAR;

