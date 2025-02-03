ALTER TABLE Dashboard
ADD  folderId BIGINT,
  ObjectId BIGINT,
  dashBoardName VARCHAR(255),
  description TEXT,
  accessType VARCHAR(50),
  dashboardType VARCHAR(50),
  sourceName VARCHAR(255),
  tableName VARCHAR(255);
