-- SQL Script to create bookings table for RMSJIMS
-- Run this script in your Supabase SQL Editor

-- ============================================
-- CREATE BOOKINGS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS bookings (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE SET NULL,
    equipment_id INT REFERENCES equipment(id) ON DELETE SET NULL,
    booker_name VARCHAR(255) NOT NULL, -- Name of the person who made the booking
    product_name VARCHAR(255), -- Equipment/product name
    product_description TEXT, -- Equipment/product description
    booking_date DATE, -- Date when booking was made/requested
    project_name VARCHAR(255) NOT NULL,
    guide_name VARCHAR(255),
    project_description TEXT,
    branch VARCHAR(255),
    department VARCHAR(255),
    team_members TEXT, -- JSON array or comma-separated list
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'pending', -- pending, approved, rejected
    admin_notes TEXT,
    rejection_reason TEXT,
    approved_by INT REFERENCES users(id) ON DELETE SET NULL,
    approved_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- Create index for faster queries
CREATE INDEX IF NOT EXISTS idx_bookings_user_id ON bookings(user_id);
CREATE INDEX IF NOT EXISTS idx_bookings_equipment_id ON bookings(equipment_id);
CREATE INDEX IF NOT EXISTS idx_bookings_status ON bookings(status);
CREATE INDEX IF NOT EXISTS idx_bookings_created_at ON bookings(created_at);

-- Add comments for documentation
COMMENT ON TABLE bookings IS 'Stores equipment booking requests from users';
COMMENT ON COLUMN bookings.status IS 'pending: awaiting admin approval, approved: admin approved, rejected: admin rejected';
COMMENT ON COLUMN bookings.team_members IS 'Comma-separated list or JSON array of team member names';

