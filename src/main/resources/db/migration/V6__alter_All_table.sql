ALTER TABLE Users
DROP COLUMN CreatedBY,groupId,roleId,objectId;

ALTER TABLE DashBoard
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE files
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE folders
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE folder_access
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE Groups
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE ObjectEntity
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);


ALTER TABLE Permission
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE Role
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);

ALTER TABLE Users
ADD createdBy VARCHAR(255),
    updatedBy VARCHAR(255),
    createdTime VARCHAR(255),
    updatedTime VARCHAR(255);
