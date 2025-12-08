-- SQL Queries for Booking Management
-- Use these queries in Supabase SQL Editor

-- ============================================
-- CREATE BOOKINGS TABLE (if not already created)
-- ============================================
-- Run CREATE_BOOKINGS_TABLE.sql first

-- ============================================
-- QUERIES FOR BOOKING OPERATIONS
-- ============================================

-- 1. Get all pending bookings (for admin approval)
SELECT * FROM bookings 
WHERE status = 'pending' 
ORDER BY created_at DESC;

-- 2. Get all bookings for a specific user
SELECT * FROM bookings 
WHERE user_id = :userId 
ORDER BY created_at DESC;

-- 3. Get all approved bookings
SELECT * FROM bookings 
WHERE status = 'approved' 
ORDER BY approved_at DESC;

-- 4. Get all rejected bookings
SELECT * FROM bookings 
WHERE status = 'rejected' 
ORDER BY created_at DESC;

-- 5. Get bookings for a specific equipment
SELECT * FROM bookings 
WHERE equipment_id = :equipmentId 
ORDER BY created_at DESC;

-- 6. Get bookings by date range
SELECT * FROM bookings 
WHERE start_date >= :startDate 
  AND end_date <= :endDate 
ORDER BY start_date ASC;

-- 7. Approve a booking (update status)
UPDATE bookings 
SET status = 'approved',
    approved_by = :adminUserId,
    approved_at = NOW(),
    updated_at = NOW()
WHERE id = :bookingId;

-- 8. Reject a booking (update status)
UPDATE bookings 
SET status = 'rejected',
    rejection_reason = :reason,
    updated_at = NOW()
WHERE id = :bookingId;

-- 9. Get booking statistics
SELECT 
    status,
    COUNT(*) as count
FROM bookings
GROUP BY status;

-- 10. Get bookings with equipment details (JOIN query)
SELECT 
    b.*,
    e.name as equipment_name,
    e.location as equipment_location,
    d.department_name,
    bu.building_name
FROM bookings b
LEFT JOIN equipment e ON b.equipment_id = e.id
LEFT JOIN departments d ON e.department_id = d.id
LEFT JOIN buildings bu ON d.building_id = bu.id
WHERE b.status = 'pending'
ORDER BY b.created_at DESC;

-- 11. Delete old rejected bookings (cleanup - optional)
-- DELETE FROM bookings 
-- WHERE status = 'rejected' 
--   AND created_at < NOW() - INTERVAL '30 days';

-- 12. Get pending bookings count (for admin dashboard)
SELECT COUNT(*) as pending_count 
FROM bookings 
WHERE status = 'pending';

