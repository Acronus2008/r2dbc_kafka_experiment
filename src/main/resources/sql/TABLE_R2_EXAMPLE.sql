CREATE TABLE "SYSTEM"."R2_EXAMPLE"
(	"ID" NUMBER NOT NULL ENABLE,
     "NAME" VARCHAR2(100 BYTE) NOT NULL ENABLE,
     "LAST_NAME" VARCHAR2(100 BYTE) NOT NULL ENABLE,
     "BIRTH_DATE" DATE NOT NULL ENABLE,
     "RUT" VARCHAR2(100 BYTE) NOT NULL ENABLE,
     CONSTRAINT "R2_EXAMPLE_PK" PRIMARY KEY ("ID")
         USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
         STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
         PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
         TABLESPACE "SYSTEM"  ENABLE
) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;