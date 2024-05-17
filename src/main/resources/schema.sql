
drop table if exists account2 CASCADE;
CREATE TABLE test
(
    `user_uid`            bigint       NOT NULL AUTO_INCREMENT,
    `id`                  varchar(255) NOT NULL,
    `pw`                  varchar(255) NOT NULL,
    `datetime_add`        datetime     NOT NULL COMMENT '추가된 시각',
    `datetime_mod`        datetime     NOT NULL COMMENT '수정된 시각',
    `datetime_last_login` datetime     NOT NULL COMMENT '마지막 로그인 시각'
);