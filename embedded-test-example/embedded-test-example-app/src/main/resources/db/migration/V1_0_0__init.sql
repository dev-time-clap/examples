create table book_entity (
  is_on_loan boolean not null, 
  id varchar(255) not null, 
  isbn varchar(255), 
  title varchar(255), 
  primary key (id)
);

create table loan_activity_entity (
  loan_activity varchar(255) check (
    loan_activity in ('LOAN_BOOK','RETURN_BOOK')
  ),
  loan_date date, 
  return_date date, 
  id varchar(255) not null, 
  isbn varchar(255), 
  primary key (id)
);

create table a1 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a2 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a3 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a4 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a5 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a6 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a7 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a8 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a9 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a10 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a11 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a12 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a13 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a14 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a15 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a16 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a17 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a18 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a19 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a20 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a21 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a22 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a23 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a24 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a25 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a26 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a27 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a28 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a29 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a30 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a31 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a32 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a33 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a34 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a35 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a36 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a37 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a38 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a39 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a40 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a41 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a42 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a43 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a44 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a45 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a46 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a47 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a48 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a49 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a50 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a51 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a52 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a53 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a54 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a55 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a56 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a57 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a58 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a59 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a60 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a61 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a62 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a63 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a64 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a65 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a66 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a67 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a68 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a69 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a70 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a71 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a72 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a73 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a74 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a75 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a76 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a77 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a78 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a79 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a80 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a81 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a82 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a83 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a84 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a85 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a86 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a87 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a88 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a89 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a90 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a91 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a92 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a93 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a94 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a95 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a96 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a97 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);

create table a98 (
  id varchar(255) not null, 
  val1 varchar(255), 
  val2 varchar(255), 
  val3 varchar(255), 
  val4 varchar(255), 
  val5 varchar(255), 
  val6 varchar(255), 
  val7 varchar(255), 
  val8 varchar(255), 
  val9 varchar(255), 
  primary key (id)
);
