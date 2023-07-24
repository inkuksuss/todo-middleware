CREATE TABLE `Member` (
                          `member_seq`	   bigint   auto_increment,
                          `member_name`	varchar(255)	NULL,
                          `member_id`	varchar(255)	NULL,
                          `member_password`	varchar(255)	NULL,
                          `member_email`	varchar(320)	NULL,
                          `is_delete`	char(1)	NULL DEFAULT  'N',
                          `member_created`	timestamp	NULL,
                          `member_updated`	timestamp	NULL
);

CREATE TABLE `Todo` (
                        `todo_seq`	bigint	auto_increment,
                        `member_seq`	bigint	NOT NULL,
                        `todo_type`	varchar(50)	NULL,
                        `todo_name`	varchar(255)	NULL,
                        `todo_content`	text	NULL,
                        `is_delete`	char(1)	NULL DEFAULT 'N',
                        `todo_created`	timestamp	NULL,
                        `todo_updated`	timestamp	NULL
);

ALTER TABLE `Member` ADD CONSTRAINT `PK_MEMBER` PRIMARY KEY (
                                                             `member_seq`
    );

ALTER TABLE `Todo` ADD CONSTRAINT `PK_TODO` PRIMARY KEY (
                                                         `todo_seq`
    );

