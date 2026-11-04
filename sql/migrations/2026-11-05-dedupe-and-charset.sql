-- ============================================================
-- 历史库升级脚本（一次性执行，执行前请备份数据库）
-- 内容：
--   1) paper_manage 去重并加唯一键（同一试卷不可重复放同一道题）
--   2) score 加唯一键（同一学生同一作业只保留一条成绩）
--   3) 全库字符集由 utf8 升级为 utf8mb4（支持 emoji/生僻字）
-- 注意：新库直接导入 online_exam.sql 即可，无需本脚本。
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ---------- 1. paper_manage 去重 ----------
CREATE TABLE `paper_manage_dedup` LIKE `paper_manage`;
INSERT INTO `paper_manage_dedup` (`paperId`, `questionType`, `questionId`)
SELECT DISTINCT `paperId`, `questionType`, `questionId` FROM `paper_manage`;
RENAME TABLE `paper_manage` TO `paper_manage_bak`, `paper_manage_dedup` TO `paper_manage`;
DROP TABLE `paper_manage_bak`;

ALTER TABLE `paper_manage`
  ADD UNIQUE INDEX `uk_paper_question` (`paperId`, `questionType`, `questionId`) USING BTREE;

-- ---------- 2. score 唯一约束 ----------
-- 若历史数据存在重复（同一 examCode + studentId 多条），请先人工确认保留哪一条：
--   SELECT examCode, studentId, COUNT(*) FROM score GROUP BY 1,2 HAVING COUNT(*) > 1;
ALTER TABLE `score`
  ADD UNIQUE INDEX `uk_score_exam_student` (`examCode`, `studentId`) USING BTREE;

-- ---------- 3. 字符集升级 ----------
ALTER TABLE `admin` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `da` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `da_question` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `exam_manage` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `fill_question` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `judge_question` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `message` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `multi_question` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `paper_manage` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `replay` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `score` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `student` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
ALTER TABLE `teacher` CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
