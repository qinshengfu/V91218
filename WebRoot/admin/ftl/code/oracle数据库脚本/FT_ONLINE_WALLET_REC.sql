-- ----------------------------
-- Table structure for "FT_ONLINE_WALLET_REC"
-- ----------------------------
-- DROP TABLE "FT_ONLINE_WALLET_REC";
CREATE TABLE "FT_ONLINE_WALLET_REC" (
	"GMT_CREATE" VARCHAR2(32 BYTE) NULL ,
	"GMT_MODIFIED" VARCHAR2(32 BYTE) NULL ,
	"PD_TYPE" VARCHAR2(55 BYTE) NULL ,
	"ORDEROX" VARCHAR2(255 BYTE) NULL ,
	"MONEY" VARCHAR2(255 BYTE) NULL ,
	"FROM_ADDRESS" VARCHAR2(255 BYTE) NULL ,
	"TO_ADDRESS" VARCHAR2(255 BYTE) NULL ,
	"ONLINE_WALLET_REC_ID" VARCHAR2(100 BYTE) NOT NULL 
)
LOGGING
NOCOMPRESS
NOCACHE
;

COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."GMT_CREATE" IS '创建时间';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."GMT_MODIFIED" IS '更新时间';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."PD_TYPE" IS '数据类型（中文）';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."ORDEROX" IS '交易哈希';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."MONEY" IS '金额';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."FROM_ADDRESS" IS '转出地址';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."TO_ADDRESS" IS '转入地址';
COMMENT ON COLUMN "FT_ONLINE_WALLET_REC"."ONLINE_WALLET_REC_ID" IS 'ID';

-- ----------------------------
-- Indexes structure for table FT_ONLINE_WALLET_REC
-- ----------------------------

-- ----------------------------
-- Checks structure for table "FT_ONLINE_WALLET_REC"

-- ----------------------------

ALTER TABLE "FT_ONLINE_WALLET_REC" ADD CHECK ("ONLINE_WALLET_REC_ID" IS NOT NULL);

-- ----------------------------
-- Primary Key structure for table "FT_ONLINE_WALLET_REC"
-- ----------------------------
ALTER TABLE "FT_ONLINE_WALLET_REC" ADD PRIMARY KEY ("ONLINE_WALLET_REC_ID");
