/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2025/4/7 15:08:16                            */
/*==============================================================*/


drop table if exists advertisement;

drop table if exists apply_for_new_movie;

drop table if exists apply_movie_type_middle;

drop table if exists cinema;

drop table if exists cinema_brand;

drop table if exists cinema_management;

drop table if exists county;

drop table if exists economize;

drop table if exists freeze;

drop table if exists market;

drop table if exists mobile_display;

drop table if exists movie;

drop table if exists movie_type;

drop table if exists movie_type_middle;

drop table if exists `order`;

drop table if exists platform_management;

drop table if exists reject;

drop table if exists screening_room;

drop table if exists slice_arrangement;

drop table if exists user;

/*==============================================================*/
/* Table: advertisement                                         */
/*==============================================================*/
create table advertisement
(
   advertisement_id     char(32) not null,
   advertisement_image  text,
   advertisement_url    text,
   advertisement_end_time datetime,
   primary key (advertisement_id)
);

/*==============================================================*/
/* Table: apply_for_new_movie                                   */
/*==============================================================*/
create table apply_for_new_movie
(
   apply_id             char(32) not null,
   platform_management_id char(32),
   cinema_management_id char(32),
   apply_reason         text,
   movie_name           varchar(30),
   movie_start_time     datetime,
   movie_duration       varchar(20),
   director             varchar(20),
   star                 varchar(30),
   introduction         text,
   score                double(10,2),
   poster_image         text,
   issuing_region       varchar(20),
   handle_state         int,
   primary key (apply_id)
);

/*==============================================================*/
/* Table: apply_movie_type_middle                               */
/*==============================================================*/
create table apply_movie_type_middle
(
   apply_id             char(32),
   movie_type_id        char(32)
);

/*==============================================================*/
/* Table: cinema                                                */
/*==============================================================*/
create table cinema
(
   cinema_id            char(32) not null,
   cinema_brand_id      char(32),
   cinema_management_id char(32),
   county_id            char(32),
   cinema_name          varchar(30),
   cinema_address       text,
   cinema_phone         varchar(30),
   cinema_email         text,
   cinema_url           text,
   cinema_points        double(10,2),
   cinema_start_time    datetime,
   cinema_end_time      datetime,
   primary key (cinema_id)
);

/*==============================================================*/
/* Table: cinema_brand                                          */
/*==============================================================*/
create table cinema_brand
(
   cinema_brand_id      char(32) not null,
   cinema_brand_name    varchar(30),
   cinema_brand_logo    text,
   primary key (cinema_brand_id)
);

/*==============================================================*/
/* Table: cinema_management                                     */
/*==============================================================*/
create table cinema_management
(
   cinema_management_id char(32) not null,
   cinema_management_email text,
   cinema_management_password varchar(20),
   cinema_management_profile_picture text,
   primary key (cinema_management_id)
);

/*==============================================================*/
/* Table: county                                                */
/*==============================================================*/
create table county
(
   county_id            char(32) not null,
   market_id            char(32),
   county_name          varchar(30),
   primary key (county_id)
);

/*==============================================================*/
/* Table: economize                                             */
/*==============================================================*/
create table economize
(
   economize_id         char(32) not null,
   economize_name       varchar(30),
   primary key (economize_id)
);

/*==============================================================*/
/* Table: freeze                                                */
/*==============================================================*/
create table freeze
(
   platform_management_id char(32),
   user_id              char(32),
   freeze_reson         text
);

/*==============================================================*/
/* Table: market                                                */
/*==============================================================*/
create table market
(
   market_id            char(32) not null,
   economize_id         char(32),
   market_name          char(30),
   primary key (market_id)
);

/*==============================================================*/
/* Table: mobile_display                                        */
/*==============================================================*/
create table mobile_display
(
   movie_id             char(32),
   mobile_display_image text,
   mobile_display_end_time datetime
);

/*==============================================================*/
/* Table: movie                                                 */
/*==============================================================*/
create table movie
(
   movie_id             char(32) not null,
   movie_name           varchar(30),
   movie_start_time     datetime,
   movie_duration       varchar(20),
   director             varchar(20),
   star                 varchar(30),
   introduction         text,
   score                double(10,2),
   poster_image         text,
   issuing_region       varchar(20),
   primary key (movie_id)
);

/*==============================================================*/
/* Table: movie_type                                            */
/*==============================================================*/
create table movie_type
(
   movie_type_id        char(32) not null,
   movie_type_name      char(20),
   primary key (movie_type_id)
);

/*==============================================================*/
/* Table: movie_type_middle                                     */
/*==============================================================*/
create table movie_type_middle
(
   movie_type_id        char(32),
   movie_id             char(32)
);

/*==============================================================*/
/* Table: `order`                                               */
/*==============================================================*/
create table `order`
(
   order_id             char(32) not null,
   user_id              char(32),
   slice_arrangement_id char(32),
   vote_all_price       double(10,2),
   seats                json,
   vote_pay_state       int default 0,
   pay_timeout          datetime,
   create_time          datetime,
   code_image           text,
   primary key (order_id)
);

/*==============================================================*/
/* Table: platform_management                                   */
/*==============================================================*/
create table platform_management
(
   platform_management_id char(32) not null,
   platform_management_account varchar(20),
   platform_management_password varchar(20),
   platform_management_name varchar(20),
   platform_management_profile_picture text,
   primary key (platform_management_id)
);

/*==============================================================*/
/* Table: reject                                                */
/*==============================================================*/
create table reject
(
   reject_id            char(32) not null,
   apply_id             char(32),
   cinema_management_id char(32),
   reject_context       text,
   primary key (reject_id)
);

/*==============================================================*/
/* Table: screening_room                                        */
/*==============================================================*/
create table screening_room
(
   screening_room_id    char(32) not null,
   cinema_id            char(32),
   screening_room_name  varchar(30),
   row_all              int,
   col_all              int,
   layout               json,
   primary key (screening_room_id)
);

/*==============================================================*/
/* Table: slice_arrangement                                     */
/*==============================================================*/
create table slice_arrangement
(
   slice_arrangement_id char(32) not null,
   cinema_id            char(32),
   movie_id             char(32),
   screening_room_id    char(32),
   slice_arrangement_start_time datetime,
   vote_price           double(10,2),
   layout               json,
   primary key (slice_arrangement_id)
);

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_id              char(32) not null,
   county_id            char(32),
   user_email           text,
   user_password        varchar(20),
   user_name            varchar(20),
   user_profile_picture text,
   user_points          int,
   user_level           int,
   user_state           int,
   primary key (user_id)
);

alter table apply_for_new_movie add constraint FK_Reference_18 foreign key (platform_management_id)
      references platform_management (platform_management_id) on delete restrict on update restrict;

alter table apply_for_new_movie add constraint FK_Reference_19 foreign key (cinema_management_id)
      references cinema_management (cinema_management_id) on delete restrict on update restrict;

alter table apply_movie_type_middle add constraint FK_Reference_20 foreign key (apply_id)
      references apply_for_new_movie (apply_id) on delete restrict on update restrict;

alter table apply_movie_type_middle add constraint FK_Reference_21 foreign key (movie_type_id)
      references movie_type (movie_type_id) on delete restrict on update restrict;

alter table cinema add constraint FK_Reference_10 foreign key (cinema_management_id)
      references cinema_management (cinema_management_id) on delete restrict on update restrict;

alter table cinema add constraint FK_Reference_11 foreign key (county_id)
      references county (county_id) on delete restrict on update restrict;

alter table cinema add constraint FK_Reference_9 foreign key (cinema_brand_id)
      references cinema_brand (cinema_brand_id) on delete restrict on update restrict;

alter table county add constraint FK_Reference_2 foreign key (market_id)
      references market (market_id) on delete restrict on update restrict;

alter table freeze add constraint FK_Reference_7 foreign key (platform_management_id)
      references platform_management (platform_management_id) on delete restrict on update restrict;

alter table freeze add constraint FK_Reference_8 foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table market add constraint FK_Reference_1 foreign key (economize_id)
      references economize (economize_id) on delete restrict on update restrict;

alter table mobile_display add constraint FK_Reference_6 foreign key (movie_id)
      references movie (movie_id) on delete restrict on update restrict;

alter table movie_type_middle add constraint FK_Reference_4 foreign key (movie_type_id)
      references movie_type (movie_type_id) on delete restrict on update restrict;

alter table movie_type_middle add constraint FK_Reference_5 foreign key (movie_id)
      references movie (movie_id) on delete restrict on update restrict;

alter table `order` add constraint FK_Reference_16 foreign key (user_id)
      references user (user_id) on delete restrict on update restrict;

alter table `order` add constraint FK_Reference_17 foreign key (slice_arrangement_id)
      references slice_arrangement (slice_arrangement_id) on delete restrict on update restrict;

alter table reject add constraint FK_Reference_22 foreign key (apply_id)
      references apply_for_new_movie (apply_id) on delete restrict on update restrict;

alter table reject add constraint FK_Reference_23 foreign key (cinema_management_id)
      references cinema_management (cinema_management_id) on delete restrict on update restrict;

alter table screening_room add constraint FK_Reference_12 foreign key (cinema_id)
      references cinema (cinema_id) on delete restrict on update restrict;

alter table slice_arrangement add constraint FK_Reference_13 foreign key (cinema_id)
      references cinema (cinema_id) on delete restrict on update restrict;

alter table slice_arrangement add constraint FK_Reference_14 foreign key (movie_id)
      references movie (movie_id) on delete restrict on update restrict;

alter table slice_arrangement add constraint FK_Reference_15 foreign key (screening_room_id)
      references screening_room (screening_room_id) on delete restrict on update restrict;

alter table user add constraint FK_Reference_3 foreign key (county_id)
      references county (county_id) on delete restrict on update restrict;

