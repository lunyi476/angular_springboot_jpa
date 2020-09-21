--DROP TABLE PRODUCT;

--CREATE TABLE PRODUCT (
--ID NUMBER(10,0) NOT NULL AUTO_INCREMENT,
--DESCRIPTION VARCHAR2(255) DEFAULT NULL,
--IMAGE_URL VARCHAR2(255) DEFAULT NULL,
--PRICE NUMBER(19,2) DEFAULT NULL,
--PRODUCT_ID VARCHAR2(255) DEFAULT NULL,
--VERSION NUMBER(10, 0) DEFAULT NULL,
--PRIMARY KEY (ID));

--DROP SEQUENCE PRODUCT_ID_SEQ;

--CREATE SEQUENCE PRODUCT_ID_SEQ
--  MINVALUE 1
--  MAXVALUE 9999999999999999
--  START WITH 100
--  INCREMENT BY 100
--  CACHE 100;

--order_no,owner,offer_no,item,description,order_date,status,buyer,supplier,vendor_name,manufacturer,carrier,delivery_terms,payment_type,payment_terms,
--currency,origin_cntry,first_ship_date,last_ship_date,lading_point,final_dest,tot_grs_value,tot_calc_cost,tot_qty,tot_grs_wgt,modify_ts, modify_user,
--CREATE TABLE improdfull.ORDERS
--(
--	ORDER_NO			INTEGER	   NOT NULL AUTO_INCREMENT
--,	OWNER				VARCHAR2(17)     NOT NULL
--,	OFFER_NO			INTEGER     NOT NULL
--,	ITEM				VARCHAR2(35)     NOT NULL
--,   DESCRIPTION         VARCHAR2(80)
--,	ORDER_DATE			DATE
--,	STATUS				VARCHAR2(8)
--,	BUYER				VARCHAR2(17)
--,	SUPPLIER			VARCHAR2(17)
--,	VENDOR_NAME			VARCHAR2(80)
--,	MANUFACTURER		VARCHAR2(17)
--,	CARRIER				VARCHAR2(17)
--,	DELIVERY_TERMS		VARCHAR2(6)
--,	PAYMENT_TYPE		VARCHAR2(6)
--,	PAYMENT_TERMS		VARCHAR2(6)
--,	CURRENCY			VARCHAR2(3)
--,	ORIGIN_CNTRY		VARCHAR2(3)
--,	FIRST_SHIP_DATE		DATE
--,	LAST_SHIP_DATE		DATE
--,	LADING_POINT		VARCHAR2(25)
--,	FINAL_DEST			VARCHAR2(17)
--,	TOT_GRS_VALUE		DECIMAL(30,6)
--,	TOT_CALC_COST		DECIMAL(30,6)
--,	TOT_QTY             DECIMAL(30,6)
--,	TOT_GRS_WGT			DECIMAL(30,6)
--,	MODIFY_TS			TIMESTAMP
--,	MODIFY_USER			VARCHAR2(18)
--,	CONSTRAINT PK_ORDER_H PRIMARY KEY (OWNER, ORDER_NO)
--) ;

--CREATE SEQUENCE REQUEST_NO_SEQ
--START WITH 100
--INCREMENT BY 1
--OWNED BY QUOTES.REQUEST_NO;


--CREATE SEQUENCE OFFER_NO_SEQ
--START WITH 100
--INCREMENT BY 1
--OWNED BY OFFERS.OFFER_NO;



--request_no,owner,item, description,request_by,category,item_class,division,brand,dept,buyer,hts_no,request_price,currency,delivery_terms,
--import_cntry,allocated_qty,modify_ts,modify_user 
CREATE TABLE QUOTES (
  REQUEST_NO      INTEGER NOT NULL AUTO_INCREMENT
 , OWNER             VARCHAR2(17)    PRIMARY KEY
 , ITEM           VARCHAR2(35)		NOT NULL
 , DESCRIPTION             VARCHAR2(80)
 , REQUESTED_BY            VARCHAR2(18)
 , CATEGORY           VARCHAR2(17)
 , ITEM_CLASS             VARCHAR2(17)
 , DIVISION          VARCHAR2(17)
 , BRAND             VARCHAR2(17)
 , DEPT              VARCHAR2(17)
 , BUYER             VARCHAR2(17)
 , HTS_NO                VARCHAR2(14)
 , REQUEST_PRICE           DECIMAL(30,6)
 , CURRENCY           VARCHAR2(3)
 , DELIVERY_TERMS     VARCHAR2(6)
 , IMPORT_CNTRY      VARCHAR2(3)
 , ALLOCATED_QTY      DECIMAL(30,6)
 , MODIFY_TS         TIMESTAMP
 , MODIFY_USER       VARCHAR2(18)
-- , CONSTRAINT PK_QUOTE PRIMARY KEY (OWNER, REQUEST_NO) 
 );
 
 
--offer_no, owner, requst_no, item, offer_date, supplier,email,offer_price, currency,payment_type,  payment_terms,delivery_terms,factory, 
--origin_cntry,lading_point, minimum_qty, commit_qty, weight,first_ship_date, effective_date,modify_ts,modify_user 
CREATE TABLE OFFERS (
    OFFER_NO    INTEGER NOT NULL AUTO_INCREMENT 
  , OWNER       VARCHAR2(17)    PRIMARY KEY 
  , REQUEST_NO     INTEGER   PRIMARY KEY
  , ITEM       VARCHAR2(35)   	   NOT NULL
  , OFFER_DATE       DATE
  , SUPPLIER       VARCHAR2(17)
  , EMAIL       VARCHAR2(80)
  , OFFER_PRICE       DECIMAL(30,6)
  , CURRENCY              VARCHAR2(3)
  , PAYMENT_TYPE              VARCHAR2(6)
  , PAYMENT_TERMS              VARCHAR2(6)
  , DELIVERY_TERMS              VARCHAR2(6)
  , FACTORY              VARCHAR2(17)
  , ORIGIN_CNTRY              VARCHAR2(3)
  ,	LADING_POINT		VARCHAR2(25)
  , MINIMUM_QTY         DECIMAL(30,6)
  , COMMIT_QTY         DECIMAL(30,6)
  , WEIGHT         DECIMAL(30,6)
  , UM_WEIGHT         VARCHAR2(6)
  , FIRST_SHIP_DATE        DATE
  , EFFECTIVE_DATE         DATE
  , MODIFY_TS    TIMESTAMP
  , MODIFY_USER    VARCHAR2(18)
 --, CONSTRAINT PK_OFFER PRIMARY KEY (OWNER, REQUEST_NO, OFFER_NO)
  , CONSTRAINT FK_OFFER FOREIGN KEY (OWNER,REQUEST_NO) REFERENCES QUOTES (OWNER, REQUEST_NO)
);
