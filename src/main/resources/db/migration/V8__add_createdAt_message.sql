-- ============================================================
-- V8__add_createAt_message.sql
-- Add the creation date and time for messages
-- ============================================================

ALTER TABLE message
    ADD COLUMN created_at DATETIME;
