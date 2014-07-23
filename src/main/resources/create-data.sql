   CREATE TABLE OrderStatusCount (
	StatusName VARCHAR(35) DEFAULT '' NOT NULL,
	StatusCount INT DEFAULT 0
);

INSERT INTO OrderStatusCount (StatusName, StatusCount) VALUES ('Work-In-Progress', 10);
INSERT INTO OrderStatusCount (StatusName, StatusCount) VALUES ('Pending Signature', 50);
   
CREATE FUNCTION MYSTOREDPROC(a VARCHAR(10), b VARCHAR(10))
	RETURNS TABLE (StatusName VARCHAR(35), StatusCount INT)
	READS SQL DATA
	RETURN TABLE (SELECT StatusName, StatusCount FROM OrderStatusCount)