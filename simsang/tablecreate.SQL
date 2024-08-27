-- 멤버 테이블 : 이승희
CREATE TABLE member (
   num   NUMBER	NOT NULL PRIMARY KEY,
   id   varchar2(50)   NOT NULL,
   pwd   varchar2(50)   NOT NULL,
   nick   varchar2(50)   NOT NULL,
   email   varchar2(50)   NOT NULL,
   tel   varchar2(50)   NOT NULL,
   address   varchar2(50)   NOT NULL,
   detailaddress   varchar2(50)   NOT NULL,
   mdate   date   NOT NULL,
   name   varchar2(50)   ,
   age   varchar2(50)  ,
   gender   varchar2(50)   ,
   imgn VARCHAR2(50)
);

-- QnA 테이블 : 정준영
CREATE TABLE qnaquestion (
	num	NUMBER	NOT NULL PRIMARY KEY,
	membernum	NUMBER	NULL,
	name	varchar2(50)	NOT NULL,
	qnaquestiondate	date	NOT NULL,
	category	varchar2(50)	NOT NULL,
	title	varchar2(50)	NOT NULL,
	content	varchar2(50)	NOT NULL,
	imgname	varchar2(100)	NULL,
	status	varchar2(100)	NULL
);

CREATE TABLE qnaanswer (
	num	NUMBER	NOT NULL PRIMARY KEY,
	qnanum	number	NOT NULL,
	content	varchar2(50)	NOT NULL,
	qnaanswerdate	date	NOT NULL
);

-- 커뮤니티 테이블 : 정준영
CREATE TABLE community (
	num	NUMBER	NOT NULL PRIMARY KEY,
	membernum	number	NOT NULL,
	name	varchar2(50)	NOT NULL,
	people	number	NULL,
	favorite	number	NULL,
	communitydate	date	NOT NULL
);

-- 대화 테이블 : 정준영
CREATE TABLE talk (
	num	NUMBER	NOT NULL PRIMARY KEY,
	communitynum	number	NOT NULL,
	membernum	number	NOT NULL,
	message	varchar2(50)	NOT NULL,
	talkdate	date	NOT NULL
);

-- 일기 테이블 : 박성호, 김건우
CREATE TABLE DIARY (
    NUM NUMBER PRIMARY KEY,
    MEMBERNUM NUMBER NOT NULL,
    EMOTION VARCHAR2(500),
    WEEK VARCHAR2(20),
    TITLE VARCHAR2(200),
    CONTENT CLOB,
    DIARYDATE DATE DEFAULT SYSDATE,
    DIARYUPDATE DATE,
    DIARYIMG VARCHAR2(255)
);

-- 감정 테이블 : 박성호
CREATE TABLE emotion (
	num	NUMBER	NOT NULL PRIMARY KEY,
	resultnum	number	NOT NULL,
	membernum	number	NOT NULL,
	name	varchar2(50)	NOT NULL,
	score	float	NOT NULL
);

-- 결과 테이블 : 박성호 (통계), 이지영, 윤수영, 허호준 (데이터 삽입)
CREATE TABLE result (
	num	NUMBER	NOT NULL PRIMARY KEY,
	membernum	number	NOT NULL,
	testspecies	varchar2(50)	NOT NULL,
	testcategory	varchar2(50)	NOT NULL,
	testvideotext	varchar2(500)	NULL,
	simulationprofile	varchar2(50)	NULL,
	simulationresult	varchar2(50)	NULL,
	selfquestionresult	varchar2(50)	NULL,
	htpimgname	varchar2(50)	NULL,
	htpresult	varchar2(50)	NULL,
	testdate	date	NOT NULL
);

-- 간편심리 테이블 : 윤수영
CREATE TABLE questionnaire (
    num NUMBER	NOT NULL PRIMARY KEY,
    question VARCHAR2(300) NOT NULL,
    cause VARCHAR2(100) NOT NULL,
    description VARCHAR2(100) NOT NULL
);

-- 간편심리 테이블 : 윤수영
CREATE TABLE useresponse (
    num NUMBER	NOT NULL PRIMARY KEY,
    userid VARCHAR2(50),
    surveycode VARCHAR(50),
    qnum NUMBER,
    respscore number,
    respdate DATE DEFAULT sysdate,
    FOREIGN KEY (qnum) REFERENCES questionnaire(num)
);

-- 시뮬레이션 테이블 : 이지영
CREATE TABLE simulation (
    num NUMBER	NOT NULL PRIMARY KEY,
    membernum NUMBER NOT NULL,
    category VARCHAR2(50) NOT NULL,
    type VARCHAR2(50) NOT NULL,
    simuldate DATE NOT NULL,
    title VARCHAR2(255) NOT NULL
);

-- 시뮬레이션 테이블 : 이지영
CREATE TABLE simuldialog (
    num NUMBER	NOT NULL PRIMARY KEY,
    simulationnum NUMBER NOT NULL,
    conversation VARCHAR2(255) NOT NULL,
    status VARCHAR2(10)
);

-- 시뮬레이션 테이블 : 이지영
CREATE TABLE simulresult (
    num NUMBER PRIMARY KEY,
    simulationnum NUMBER NOT NULL,
    emotionname VARCHAR2(50) NOT NULL,
    emotionscore NUMBER NOT NULL,
    stressscore NUMBER NOT NULL,
    heartrate NUMBER NOT NULL,
    topemotion VARCHAR2(50) NOT NULL,
    topemotionscore NUMBER NOT null,
    topemotionimg VARCHAR2(255),
    evaluation VARCHAR2(2000)
);

--장바구니  테이블 생성 : 이승희
CREATE TABLE cart (
   membernum   number   NOT NULL,
   productnum   varchar2(50)  NOT NULL,
   count   number   NOT NULL,
   cdate DATE,
   CONSTRAINT fk_cart_membernum FOREIGN KEY (membernum) REFERENCES member(num),
    CONSTRAINT uq_cart_member_product UNIQUE (membernum, productnum)
);

--주문완료  테이블 생성 : 이승희
CREATE TABLE request (
   membernum   number  NOT NULL,
   requestnum   number  UNIQUE NOT NULL,
   address   varchar2(50)   NOT NULL,
   detailaddress   varchar2(50)   NOT NULL,
   addrequest   varchar2(50)   NOT NULL,
   way   varchar2(50)   NOT NULL,
   waycorp   varchar2(50)   NOT NULL,
   waynum   varchar2(50)   NOT NULL,
   rdate DATE,
   CONSTRAINT fk_request_membernum FOREIGN KEY (membernum) REFERENCES member(num)
);

--주문상품 테이블 생성 : 이승희
CREATE TABLE orders (
ordernum   number   UNIQUE NOT NULL,
   requestnum   number   NOT NULL,
   membernum   number   NOT NULL,
   productnum   varchar2(50)   NOT NULL,
   name   varchar2(50)   NOT NULL,
   count   number   NOT NULL,
   price   number   NOT NULL,
   odate DATE,
   img varchar2(50) ,
   delivery varchar2(50),
   CONSTRAINT fk_orders_membernum FOREIGN KEY (membernum) REFERENCES member(num)
);

--배송지 테이블 생성 : 이승희
CREATE TABLE address (
   num NUMBER UNIQUE NOT NULL,
   membernum   number   NOT NULL,
   addressnick  varchar2(50)   NOT NULL,
   address   varchar2(50)   NOT NULL,
   detailaddress varchar2(50)   NOT NULL,
   ADDREQUeST  varchar2(50)   NOT NULL,
   CONSTRAINT fk_address_membernum FOREIGN KEY (membernum) REFERENCES member(num)
);

 --Review 테이블 : 송지미
CREATE TABLE review (
    review_num NUMBER(10) PRIMARY KEY, 
    member_num NUMBER ,
    review_content CLOB,               
    review_productnum VARCHAR2(255),    
    review_stars NUMBER(2),          
    review_date DATE,               
    review_imagename VARCHAR2(255),   
    review_category VARCHAR2(255)    
);

-- 상품 테이블 생성 : 송지미
CREATE TABLE product (
    product_num NUMBER(10) PRIMARY KEY, 
    product_category VARCHAR2(255), 
    product_image VARCHAR2(255), 
    product_name VARCHAR2(255),
    product_price NUMBER(10, 2), 
    product_stock NUMBER, 
    product_manufacturer VARCHAR2(255), 
    product_registrant VARCHAR2(255),
    product_hit NUMBER 
);


--결제수단 테이블 생성 : 이승희
CREATE TABLE payment (
   num   number UNIQUE NOT NULL,
   membernum   number NOT NULL,
   paynick   varchar2(50) NOT NULL,
   payway   varchar2(50) NOT NULL,
   paycorp   varchar2(50) ,
   paynum   number NOT NULL,
   payday varchar2(50) ,
   paycvc number,
   cdate DATE,
   CONSTRAINT fk_payment_membernum FOREIGN KEY (membernum) REFERENCES member(num)
);

 --마켓 QNA 테이블 : 김건우
CREATE TABLE marketqnaquestion (
   num NUMBER PRIMARY KEY, 
   membernum NUMBER NULL,
   name varchar2(50) NOT NULL,
   qnaquestiondate date NOT NULL,
   category varchar2(50) NOT NULL,
   title varchar2(50) NOT NULL,
   content varchar2(50) NOT NULL,
   imgname varchar2(100) NULL,
   status varchar2(100) NULL
);

 --마켓 QNA 테이블 : 김건우
CREATE TABLE marketqnaanswer (
   num number PRIMARY KEY,
   qnanum number NOT NULL,
   content varchar2(50) NOT NULL,
   qnaanswerdate date NOT NULL
);


ALTER TABLE qnaquestion ADD CONSTRAINT fk_qnamembernum FOREIGN KEY (membernum) REFERENCES member(num);
ALTER TABLE talk ADD CONSTRAINT fk_talk FOREIGN KEY (membernum) REFERENCES member(num);
ALTER TABLE talk ADD CONSTRAINT fk_talk_comm FOREIGN KEY (communitynum) REFERENCES community(num);
ALTER TABLE diary ADD CONSTRAINT fk_membernum FOREIGN KEY (membernum) REFERENCES member(num);
ALTER TABLE simuldialog ADD CONSTRAINT FK_simulationnum FOREIGN KEY (simulationnum) REFERENCES simulation(num) ON DELETE CASCADE;
ALTER TABLE simulresult ADD CONSTRAINT FK_simulresult_simulationnum FOREIGN KEY (simulationnum) REFERENCES simulation(num) ON DELETE CASCADE;
ALTER TABLE marketqnaquestion ADD CONSTRAINT fk_membernum_mq FOREIGN KEY (membernum) REFERENCES member(num);


CREATE SEQUENCE member_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE qnaquestion_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE qnaanswer_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE community_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE hashtag_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE talk_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE diary_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE emotion_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE result_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE questionnaire_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE useresponse_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE simulation_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE simuldialog_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE simulresult_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE ordernum_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE product_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE review_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE address_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE payment_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE marketqnaquestion_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE marketqnaanswer_seq START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE TRIGGER trg_diary_to_result
AFTER INSERT OR UPDATE ON DIARY
FOR EACH ROW
BEGIN
    INSERT INTO result (
        num, 
        membernum, 
        testspecies, 
        testcategory, 
        testvideotext, 
        testdate
    ) VALUES (
        result_seq.NEXTVAL, -- Assume there is a sequence named result_seq
        :NEW.MEMBERNUM, 
        'diary', 
        'diary', 
        :NEW.CONTENT, 
        SYSDATE
    );
END;
/
COMMIT;
ALTER TABLE result MODIFY TESTVIDEOTEXT VARCHAR2(500);

INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,1,'마음의 치유가 필요한 사람',1400,300,TO_DATE('2020-02-21','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,2,'편히 얘기할 곳',1500,400,TO_DATE('2024-05-21','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,3,'충전하는 시간',1600,500,TO_DATE('2024-05-25','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,4,'울적할 때 와요',1700,600,TO_DATE('2024-03-07','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,5,'재미있는 사람들',1800,700,TO_DATE('2024-02-21','YYYY-MM-DD'));