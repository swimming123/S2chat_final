-- COMMUNICATION 데이터
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,1,'마음의 치유가 필요한 사람',1400,300,TO_DATE('2020-02-21','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,2,'편히 얘기할 곳',1500,400,TO_DATE('2024-05-21','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,3,'충전하는 시간',1600,500,TO_DATE('2024-05-25','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,4,'울적할 때 와요',1700,600,TO_DATE('2024-03-07','YYYY-MM-DD'));
INSERT INTO COMMUNITY(num,membernum,name,people,favorite,communitydate) VALUES(community_seq.NEXTVAL,5,'재미있는 사람들',1800,700,TO_DATE('2024-02-21','YYYY-MM-DD'));

-- simpletest 테스트 데이터
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '평소에는 성가시지 않았던 일이 성가시게 느껴졌나요?', '신체저하', '귀찮음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '별로 먹고 싶지 않았나요?', '신체저하', '식욕부진');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '입맛이 없었나요?', '신체저하', '식욕부진');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '가족이나 친구가 도와주더라도 울적한 기분을 떨칠 수 없었나요?', '신체저하', '울적한 기분');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '나도 다른 사람만큼 기분이 좋았나요?', '긍정정서', '능력있음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '하고 있는 일에 마음을 집중하기가 어려웠나요?', '신체저하', '집중곤란');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '우울했나요?', '신체저하', '우울');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '하는 일마다 힘들게 느꼈나요?', '신체저하', '힘듦');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '미래에 대해 희망적으로 느꼈나요?', '긍정정서', '능력있음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '내 인생은 실패작이라고 생각했나요?', '대인관계', '실패');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '무서움을 느꼈나요?', '우울정서', '두려움');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '잠을 설쳤나요?', '우울정서', '수면');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '행복했나요?', '긍정정서', '능력있음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '평소보다 말을 적게 했나요?', '신체저하', '말없음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '외로움을 느꼈나요?', '대인관계', '외로움');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '사람들이 불친절했나요?', '대인관계', '차가움');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '인생이 즐거웠나요?', '긍정정서', '능력있음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '울음을 터뜨린 적이 있었나요?.', '우울정서', '울음');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '슬픔을 느꼈나요?', '우울정서', '슬픔');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '사람들이 나를 싫어한다고 느꼈나요?', '대인관계', '싫어함');
INSERT INTO questionnaire (num, question, cause, description) VALUES (questionnaire_seq.NEXTVAL, '일을 제대로 진척시킬 수 없었나요?', '신체저하', '기운없음');

--insert into useresponse (num, userid, surveycode, qnum, respscore, respdate)  VALUES (useresponse_seq.NEXTVAL, 'testid', 'CESD', 1, 0, sysdate);
--insert into useresponse (num, userid, surveycode, qnum, respscore, respdate)  VALUES (useresponse_seq.NEXTVAL, 'testid', 'CESD', 2, 1, sysdate);
--insert into useresponse (num, userid, surveycode, qnum, respscore, respdate)  VALUES (useresponse_seq.NEXTVAL, 'testid2', 'CESD', 1, 1, sysdate);
--insert into useresponse (num, userid, surveycode, qnum, respscore, respdate)  VALUES (useresponse_seq.NEXTVAL, 'testid2', 'CESD', 2, 1, sysdate);


-- diary테이블 (유저 2명)
INSERT INTO member VALUES (member_seq.nextval, 'test1', 'test1', 'test1', 'test1@mail.com', '01011112222', '서울시 강남구', 'test1 빌딩', SYSDATE, '테스트', '30', 'F', 'image1.jpg');
INSERT INTO member VALUES (member_seq.nextval, 'test2', 'test2', 'test2', 'test2@mail.com', '01099999999', '서울시 강남구 test2', 'test2 빌딩', SYSDATE, '테스트2', '25', 'M', 'image2.jpg');

INSERT INTO diary VALUES (diary_seq.nextval, 1, '행복하다', '월요일', '첫 번째 일기', '오늘은 정말 행복한 날이었다.', sysdate, sysdate, '/images/path1.png', '/images/image1.png');
INSERT INTO diary VALUES (diary_seq.nextval, 1, '행복하다', '화요일', '두 번째 일기', '오늘은 정말 행복한.', sysdate+1, sysdate+1, '/images/path2.png', '/images/image2.png');
INSERT INTO diary VALUES (diary_seq.nextval, 1, '행복하다', '수요일', '세 번째 일기', '오늘은 정말 행복할까', sysdate+2, sysdate+2, '/images/path3.png', '/images/image3.png');
INSERT INTO diary VALUES (diary_seq.nextval, 2, '행복하다', '토요일', '네 번째 일기', '오늘은 정말 행복하고싶다.', sysdate, sysdate, '/images/path4.png', '/images/image4.png');
INSERT INTO diary VALUES (diary_seq.nextval, 2, '행복하다', '일요일', '다섯 번째 일기', '오늘은 정말.', sysdate, sysdate, '/images/path5.png', '/images/image5.png');



-- simulation 테이블
INSERT INTO simulation (num, membernum, category, type, simuldate, title) VALUES (simulation_seq.NEXTVAL, 1, '다툼', '텍스트', SYSDATE, '친구와 다툰 상황');
INSERT INTO simuldialog (num, simulationnum, conversation, status) VALUES (1, 1, '너가 어제 말 안하고 가서 기분이 안좋았어', 'g');
INSERT INTO simulresult (num, simulationnum, emotionname, emotionscore, stressscore, heartrate, topemotion, topemotionscore, topemotionimg, evaluation) 
VALUES (simulresult_seq.NEXTVAL, 1, '행복', 1, 1, 85, '행복', 45, '행복.png', '평가1');
INSERT INTO simulation (num, membernum, category, type, simuldate, title) VALUES (simulation_seq.NEXTVAL, 1, '다툼', '텍스트', SYSDATE, '친구와 다툰 상황');
INSERT INTO simuldialog (num, simulationnum, conversation, status) VALUES (2, 2, '어제의 일로 인해 오늘은 대화가 필요해.', 'u');
INSERT INTO simulresult (num, simulationnum, emotionname, emotionscore, stressscore, heartrate, topemotion, topemotionscore, topemotionimg, evaluation) 
VALUES (simulresult_seq.NEXTVAL, 2, '상처', 1, 1, 85, '행복', 45, '행복.png', '평가2');
INSERT INTO simulation (num, membernum, category, type, simuldate, title) VALUES (simulation_seq.NEXTVAL, 1, '다툼', '텍스트', SYSDATE, '친구와 다툰 상황');
INSERT INTO simuldialog (num, simulationnum, conversation, status) VALUES (3, 3, '내일 만나서 다시 이야기하자.', 'g');
INSERT INTO simulresult (num, simulationnum, emotionname, emotionscore, stressscore, heartrate, topemotion, topemotionscore, topemotionimg, evaluation) 
VALUES (simulresult_seq.NEXTVAL, 3, '불안', 1, 1, 85, '행복', 45, '행복.png', '평가3');
INSERT INTO simulation (num, membernum, category, type, simuldate, title) VALUES (simulation_seq.NEXTVAL, 1, '다툼', '텍스트', SYSDATE, '친구와 다툰 상황');
INSERT INTO simuldialog (num, simulationnum, conversation, status) VALUES (4, 4, '이번에는 서로 이해해보자.', 'u');
INSERT INTO simulresult (num, simulationnum, emotionname, emotionscore, stressscore, heartrate, topemotion, topemotionscore, topemotionimg, evaluation) 
VALUES (simulresult_seq.NEXTVAL, 4, '행복', 1, 1, 85, '행복', 45, '행복.png', '평가4');
INSERT INTO simulation (num, membernum, category, type, simuldate, title) VALUES (simulation_seq.NEXTVAL, 1, '다툼', '텍스트', SYSDATE, '친구와 다툰 상황');
INSERT INTO simuldialog (num, simulationnum, conversation, status) VALUES (5, 5, '이제는 서로 이해할 수 있을 것 같아.', 'g');
INSERT INTO simulresult (num, simulationnum, emotionname, emotionscore, stressscore, heartrate, topemotion, topemotionscore, topemotionimg, evaluation) 
VALUES (simulresult_seq.NEXTVAL, 5, '무감정', 1, 1, 85, '행복', 45, '행복.png', '평가5');


--QNAQUESTION테이블
INSERT INTO QNAQUESTION VALUES(qnaquestion_seq.nextval,1,'정준영1',TO_DATE('2024-08-06', 'YYYY-MM-DD'),'category1','title1','content1','img.jpg','답변대기중');
INSERT INTO QNAQUESTION VALUES(qnaquestion_seq.nextval,2,'정준영2',TO_DATE('2024-08-07', 'YYYY-MM-DD'),'category2','title2','content2','img.jpg','답변대기중');
INSERT INTO QNAQUESTION VALUES(qnaquestion_seq.nextval,1,'정준영3',TO_DATE('2024-08-08', 'YYYY-MM-DD'),'category3','title3','content3','img.jpg','답변대기중');
INSERT INTO QNAQUESTION VALUES(qnaquestion_seq.nextval,2,'정준영4',TO_DATE('2024-08-09', 'YYYY-MM-DD'),'category4','title4','content4','img.jpg','답변대기중');
INSERT INTO QNAQUESTION VALUES(qnaquestion_seq.nextval,2,'정준영5',TO_DATE('2024-08-10', 'YYYY-MM-DD'),'category5','title5','content5','img.jpg','답변대기중');

COMMIT;
