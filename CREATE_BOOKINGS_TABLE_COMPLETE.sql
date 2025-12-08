-- SQL Script to create bookings table for RMSJIMS with all required fields
-- Run this script in your Supabase SQL Editor

-- ============================================
-- DROP EXISTING TABLE (if you need to recreate)
-- ============================================
-- DROP TABLE IF EXISTS bookings CASCADE;

-- ============================================
-- CREATE BOOKINGS TABLE
-- ============================================

CREATE TABLE IF NOT EXISTS bookings (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE SET NULL,
    equipment_id INT REFERENCES equipment(id) ON DELETE SET NULL,
    booker_name VARCHAR(255) NOT NULL, -- Name of the person who made the booking (staff/assistant)
    product_name VARCHAR(255), -- Equipment/product name
    product_description TEXT, -- Equipment/product description
    booking_date DATE DEFAULT CURRENT_DATE, -- Date when booking was made/requested
    project_name VARCHAR(255) NOT NULL,
    guide_name VARCHAR(255),
    project_description TEXT,
    branch VARCHAR(255),
    department VARCHAR(255),
    team_members TEXT, -- Comma-separated list of team member names
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

-- ============================================
-- CREATE INDEXES FOR PERFORMANCE
-- ============================================

CREATE INDEX IF NOT EXISTS idx_bookings_user_id ON bookings(user_id);
CREATE INDEX IF NOT EXISTS idx_bookings_equipment_id ON bookings(equipment_id);
CREATE INDEX IF NOT EXISTS idx_bookings_status ON bookings(status);
CREATE INDEX IF NOT EXISTS idx_bookings_created_at ON bookings(created_at);
CREATE INDEX IF NOT EXISTS idx_bookings_booking_date ON bookings(booking_date);
CREATE INDEX IF NOT EXISTS idx_bookings_booker_name ON bookings(booker_name);

-- ============================================
-- ADD COMMENTS FOR DOCUMENTATION
-- ============================================

COMMENT ON TABLE bookings IS 'Stores equipment booking requests from users (staff/assistant)';
COMMENT ON COLUMN bookings.status IS 'pending: awaiting admin approval, approved: admin approved, rejected: admin rejected';
COMMENT ON COLUMN bookings.booker_name IS 'Name of the person (staff/assistant) who made the booking request';
COMMENT ON COLUMN bookings.product_name IS 'Name of the equipment/product being booked';
COMMENT ON COLUMN bookings.product_description IS 'Description or location of the equipment/product';
COMMENT ON COLUMN bookings.booking_date IS 'Date when the booking request was made';
COMMENT ON COLUMN bookings.team_members IS 'Comma-separated list of team member names';

-- ============================================
-- SAMPLE QUERIES
-- ============================================

-- Get all pending bookings with booker name and product details
-- SELECT 
--     id,
--     booker_name,
--     product_name,
--     product_description,
--     booking_date,
--     status,
--     equipment_id,
--     project_name
-- FROM bookings 
-- WHERE status = 'pending' 
-- ORDER BY created_at DESC;

-- Get bookings by booker name
-- SELECT * FROM bookings 
-- WHERE booker_name = 'John Doe' 
-- ORDER BY booking_date DESC;

-- Get bookings by product name
-- SELECT * FROM bookings 
-- WHERE product_name LIKE '%Camera%' 
-- ORDER BY booking_date DESC;

