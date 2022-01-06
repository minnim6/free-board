INSERT INTO member
(member_nickname, member_join_date, member_status, member_signup_category, member_email, member_sns_id, member_refresh_token, member_refresh_token_expire_time)
VALUES( '회원1', '2021-11-24', 'Y', 'KAKAO', '', '1', '', NULL);
INSERT INTO role
(member_number, `role`)
VALUES(1, 'ROLE_MEMBER');
INSERT INTO role
(member_number, `role`)
VALUES(1, 'ROLE_ADMIN');
INSERT INTO post
(member_number, post_create_date, post_title, post_contents, post_category, post_amend_date, post_status, post_report_count)
VALUES(1, '2021-11-24', '제목', '내용', '카테고리', '2021-11-24', 'Y', 0);
INSERT INTO post
(member_number, post_create_date, post_title, post_contents, post_category, post_amend_date, post_status, post_report_count)
VALUES(1, '2021-11-24', '제목', '내용', '카테고리', '2021-11-24', 'Y', 0);
INSERT INTO post
(member_number, post_create_date, post_title, post_contents, post_category, post_amend_date, post_status, post_report_count)
VALUES(1, '2021-11-24', '제목', '내용', '카테고리', '2021-11-24', 'Y', 0);
insert into comment
(post_number,member_number,comment_contents,comment_create_date,comment_amend_date)
values(1,1,'내용','2021-11-24','2021-11-24');
insert into comment
(post_number,member_number,comment_contents,comment_create_date,comment_amend_date)
values(2,1,'내용','2021-11-24','2021-11-24');
insert into comment
(post_number,member_number,comment_contents,comment_create_date,comment_amend_date)
values(2,1,'내용','2021-11-24','2021-11-24');
insert into recomment
(post_number,member_number,comment_number,recomment_contents,recomment_create_date,recomment_amend_date)
values(1,1,1,'내용','2021-11-24','2021-11-24');
insert into recomment
(post_number,member_number,comment_number,recomment_contents,recomment_create_date,recomment_amend_date)
values(2,1,3,'내용','2021-11-24','2021-11-24');
insert into question_board
(member_number,question_board_title,question_board_contents,question_board_create_date)
 values(1,'title','contents','2021-11-24');
insert into question_board
(member_number,question_board_title,question_board_contents,question_board_create_date)
values(1,'title','contents','2021-11-24');
insert into sanctions
(sanctions_Key,sanctions_value,sanctions_contents)
values ('게시물게시물',1,'내용내내용');