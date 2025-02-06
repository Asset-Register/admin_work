ALTER TABLE role
DROP CONSTRAINT FKi6irifu5kvmxh739m2efdb2vq;

ALTER TABLE role
ALTER COLUMN permissionId BIGINT NULL;

UPDATE role
SET permissionId = NULL
WHERE roleId IN (1, 2);


ALTER TABLE Role
DROP COLUMN permissionId;

