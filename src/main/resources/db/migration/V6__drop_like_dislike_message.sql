-- ============================================================
-- V5__drop_like_dislike.sql
-- Permanent removal of like and dislike attributes
-- ============================================================

ALTER TABLE message
    DROP COLUMN like_count;

ALTER TABLE message
    DROP COLUMN dislike_count;
