-- ============================================================
-- V5__drop_like_dislike.sql
-- Permanent removal of like and dislike attributes
-- ============================================================

ALTER TABLE thread
    DROP COLUMN like_count;

ALTER TABLE thread
    DROP COLUMN dislike_count;
