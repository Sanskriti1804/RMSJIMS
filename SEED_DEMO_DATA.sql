-- SQL Script to create tables and insert demo data for RMSJIMS
-- Run this script in your Supabase SQL Editor

-- ============================================
-- CREATE TABLES
-- ============================================

CREATE TABLE IF NOT EXISTS buildings (
    id SERIAL PRIMARY KEY,
    building_name VARCHAR(255) NOT NULL,
    building_number VARCHAR(50),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS departments (
    id SERIAL PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL,
    address TEXT,
    building_id INT REFERENCES buildings(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS rooms (
    id SERIAL PRIMARY KEY,
    room_name VARCHAR(255) NOT NULL,
    room_number VARCHAR(50),
    location VARCHAR(255),
    capacity INT,
    status VARCHAR(50),
    building_id INT REFERENCES buildings(id) ON DELETE SET NULL,
    department_id INT REFERENCES departments(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS equipment (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image TEXT,
    location VARCHAR(255),
    request_status VARCHAR(100),
    request_urgency VARCHAR(100),
    incharge_id INT,
    incharge_name VARCHAR(255),
    incharge_designation VARCHAR(255),
    incharge_email VARCHAR(255),
    incharge_phone VARCHAR(50),
    booking_date DATE,
    requester_name VARCHAR(255),
    department_id INT REFERENCES departments(id) ON DELETE SET NULL,
    room_id INT REFERENCES rooms(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tickets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50),
    urgency VARCHAR(50),
    requester_name VARCHAR(255),
    assigned_to VARCHAR(255),
    department_id INT REFERENCES departments(id) ON DELETE SET NULL,
    equipment_id INT REFERENCES equipment(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

-- ============================================
-- CREATE INDEXES
-- ============================================

CREATE INDEX IF NOT EXISTS idx_departments_building_id ON departments(building_id);
CREATE INDEX IF NOT EXISTS idx_rooms_building_id ON rooms(building_id);
CREATE INDEX IF NOT EXISTS idx_rooms_department_id ON rooms(department_id);
CREATE INDEX IF NOT EXISTS idx_equipment_department_id ON equipment(department_id);
CREATE INDEX IF NOT EXISTS idx_equipment_room_id ON equipment(room_id);
CREATE INDEX IF NOT EXISTS idx_tickets_department_id ON tickets(department_id);
CREATE INDEX IF NOT EXISTS idx_tickets_equipment_id ON tickets(equipment_id);

-- ============================================
-- CREATE TRIGGERS FOR UPDATED_AT
-- ============================================

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_buildings_updated_at BEFORE UPDATE ON buildings
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_departments_updated_at BEFORE UPDATE ON departments
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_rooms_updated_at BEFORE UPDATE ON rooms
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_equipment_updated_at BEFORE UPDATE ON equipment
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tickets_updated_at BEFORE UPDATE ON tickets
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- ============================================
-- INSERT BUILDINGS (6 buildings)
-- ============================================

INSERT INTO buildings (building_name, building_number) VALUES
('Main Academic Building', 'A'),
('Science and Technology Center', 'B'),
('Engineering Complex', 'C'),
('Administration Building', 'D'),
('Library and Research Center', 'E'),
('Student Center', 'F')
ON CONFLICT DO NOTHING;

-- ============================================
-- INSERT DEPARTMENTS (12 departments)
-- ============================================

INSERT INTO departments (department_name, address, building_id) VALUES
('Computer Science', 'Floor 2, Main Academic Building', 1),
('Mathematics', 'Floor 1, Main Academic Building', 1),
('Physics', 'Ground Floor, Science and Technology Center', 2),
('Chemistry', 'Floor 1, Science and Technology Center', 2),
('Biology', 'Floor 2, Science and Technology Center', 2),
('Mechanical Engineering', 'Building C, Wing A', 3),
('Electrical Engineering', 'Building C, Wing B', 3),
('Civil Engineering', 'Building C, Wing C', 3),
('Management Studies', 'Floor 3, Administration Building', 4),
('MCA Department', 'Floor 4, Main Academic Building', 1),
('BCA Department', 'Floor 3, Main Academic Building', 1),
('Research and Development', 'Floor 2, Library and Research Center', 5)
ON CONFLICT DO NOTHING;

-- ============================================
-- INSERT ROOMS (20 rooms)
-- ============================================

INSERT INTO rooms (room_name, room_number, location, capacity, status, building_id, department_id) VALUES
-- Computer Science Department
('Computer Lab 1', 'A-201', 'Floor 2, Building A', 30, 'Available', 1, 1),
('Computer Lab 2', 'A-202', 'Floor 2, Building A', 30, 'Available', 1, 1),
('Server Room', 'A-203', 'Floor 2, Building A', 5, 'In Use', 1, 1),
('Project Lab', 'A-204', 'Floor 2, Building A', 20, 'Available', 1, 1),

-- Mathematics Department
('Math Lab', 'A-101', 'Floor 1, Building A', 25, 'Available', 1, 2),
('Seminar Hall', 'A-102', 'Floor 1, Building A', 50, 'Available', 1, 2),

-- Physics Department
('Physics Lab 1', 'B-001', 'Ground Floor, Building B', 20, 'Available', 2, 3),
('Physics Lab 2', 'B-002', 'Ground Floor, Building B', 20, 'In Use', 2, 3),
('Electronics Lab', 'B-003', 'Ground Floor, Building B', 15, 'Available', 2, 3),

-- Chemistry Department
('Chemistry Lab 1', 'B-101', 'Floor 1, Building B', 25, 'Available', 2, 4),
('Chemistry Lab 2', 'B-102', 'Floor 1, Building B', 25, 'Available', 2, 4),
('Storage Room', 'B-103', 'Floor 1, Building B', 10, 'Available', 2, 4),

-- Biology Department
('Biology Lab', 'B-201', 'Floor 2, Building B', 20, 'Available', 2, 5),
('Microbiology Lab', 'B-202', 'Floor 2, Building B', 15, 'Available', 2, 5),

-- Engineering Departments
('Mechanical Workshop', 'C-A-101', 'Building C, Wing A', 30, 'In Use', 3, 6),
('CAD Lab', 'C-A-102', 'Building C, Wing A', 25, 'Available', 3, 6),
('Electrical Lab', 'C-B-101', 'Building C, Wing B', 20, 'Available', 3, 7),
('Circuit Design Lab', 'C-B-102', 'Building C, Wing B', 20, 'Available', 3, 7),
('Civil Engineering Lab', 'C-C-101', 'Building C, Wing C', 25, 'Available', 3, 8),

-- Management Studies
('Business Lab', 'D-301', 'Floor 3, Building D', 30, 'Available', 4, 9)
ON CONFLICT DO NOTHING;

-- ============================================
-- INSERT EQUIPMENT (20 equipment items)
-- ============================================

INSERT INTO equipment (name, image, location, request_status, request_urgency, incharge_id, incharge_name, incharge_designation, incharge_email, incharge_phone, booking_date, requester_name, department_id, room_id) VALUES
-- Computer Science Equipment
('Dell OptiPlex 7090 Desktop', 'https://example.com/images/dell-optiplex.jpg', 'Computer Lab 1, A-201', 'Available', 'Low', 1, 'Dr. John Smith', 'Lab Incharge', 'john.smith@college.edu', '+91-9876543210', NULL, NULL, 1, 1),
('HP LaserJet Pro Printer', 'https://example.com/images/hp-printer.jpg', 'Computer Lab 1, A-201', 'In Use', 'Medium', 1, 'Dr. John Smith', 'Lab Incharge', 'john.smith@college.edu', '+91-9876543210', '2024-01-15', 'Alice Johnson', 1, 1),
('Cisco Network Switch 24-Port', 'https://example.com/images/cisco-switch.jpg', 'Server Room, A-203', 'Available', 'High', 1, 'Dr. John Smith', 'Lab Incharge', 'john.smith@college.edu', '+91-9876543210', NULL, NULL, 1, 3),
('Projector Epson EB-X41', 'https://example.com/images/epson-projector.jpg', 'Project Lab, A-204', 'Available', 'Medium', 1, 'Dr. John Smith', 'Lab Incharge', 'john.smith@college.edu', '+91-9876543210', NULL, NULL, 1, 4),

-- Physics Equipment
('Oscilloscope Tektronix TBS1000', 'https://example.com/images/oscilloscope.jpg', 'Physics Lab 1, B-001', 'Available', 'High', 2, 'Dr. Sarah Williams', 'Physics Lab Coordinator', 'sarah.williams@college.edu', '+91-9876543211', NULL, NULL, 3, 7),
('Function Generator', 'https://example.com/images/function-gen.jpg', 'Physics Lab 1, B-001', 'In Use', 'Medium', 2, 'Dr. Sarah Williams', 'Physics Lab Coordinator', 'sarah.williams@college.edu', '+91-9876543211', '2024-01-16', 'Bob Miller', 3, 7),
('Digital Multimeter Fluke 87V', 'https://example.com/images/multimeter.jpg', 'Electronics Lab, B-003', 'Available', 'Low', 2, 'Dr. Sarah Williams', 'Physics Lab Coordinator', 'sarah.williams@college.edu', '+91-9876543211', NULL, NULL, 3, 9),

-- Chemistry Equipment
('Analytical Balance Mettler Toledo', 'https://example.com/images/balance.jpg', 'Chemistry Lab 1, B-101', 'Available', 'High', 3, 'Dr. Michael Brown', 'Chemistry Head', 'michael.brown@college.edu', '+91-9876543212', NULL, NULL, 4, 11),
('pH Meter Hanna Instruments', 'https://example.com/images/ph-meter.jpg', 'Chemistry Lab 1, B-101', 'Available', 'Medium', 3, 'Dr. Michael Brown', 'Chemistry Head', 'michael.brown@college.edu', '+91-9876543212', NULL, NULL, 4, 11),
('Hot Plate Stirrer', 'https://example.com/images/hotplate.jpg', 'Chemistry Lab 2, B-102', 'In Use', 'Low', 3, 'Dr. Michael Brown', 'Chemistry Head', 'michael.brown@college.edu', '+91-9876543212', '2024-01-17', 'Carol White', 4, 12),

-- Biology Equipment
('Microscope Olympus CX23', 'https://example.com/images/microscope.jpg', 'Biology Lab, B-201', 'Available', 'High', 4, 'Dr. Emily Davis', 'Biology Lab Incharge', 'emily.davis@college.edu', '+91-9876543213', NULL, NULL, 5, 13),
('Centrifuge Machine', 'https://example.com/images/centrifuge.jpg', 'Microbiology Lab, B-202', 'Available', 'Medium', 4, 'Dr. Emily Davis', 'Biology Lab Incharge', 'emily.davis@college.edu', '+91-9876543213', NULL, NULL, 5, 14),
('Autoclave Sterilizer', 'https://example.com/images/autoclave.jpg', 'Microbiology Lab, B-202', 'In Use', 'High', 4, 'Dr. Emily Davis', 'Biology Lab Incharge', 'emily.davis@college.edu', '+91-9876543213', '2024-01-18', 'David Lee', 5, 14),

-- Engineering Equipment
('CNC Milling Machine', 'https://example.com/images/cnc-mill.jpg', 'Mechanical Workshop, C-A-101', 'Available', 'Critical', 5, 'Prof. Robert Taylor', 'Workshop Supervisor', 'robert.taylor@college.edu', '+91-9876543214', NULL, NULL, 6, 15),
('3D Printer Ultimaker S3', 'https://example.com/images/3d-printer.jpg', 'CAD Lab, C-A-102', 'Available', 'Medium', 5, 'Prof. Robert Taylor', 'Workshop Supervisor', 'robert.taylor@college.edu', '+91-9876543214', NULL, NULL, 6, 16),
('Power Supply Unit', 'https://example.com/images/power-supply.jpg', 'Electrical Lab, C-B-101', 'Available', 'Low', 6, 'Dr. Lisa Anderson', 'Electrical Lab Head', 'lisa.anderson@college.edu', '+91-9876543215', NULL, NULL, 7, 17),
('Digital Storage Oscilloscope', 'https://example.com/images/dso.jpg', 'Circuit Design Lab, C-B-102', 'In Use', 'High', 6, 'Dr. Lisa Anderson', 'Electrical Lab Head', 'lisa.anderson@college.edu', '+91-9876543215', '2024-01-19', 'Frank Wilson', 7, 18),
('Universal Testing Machine', 'https://example.com/images/utm.jpg', 'Civil Engineering Lab, C-C-101', 'Available', 'High', 7, 'Dr. James Martinez', 'Civil Lab Coordinator', 'james.martinez@college.edu', '+91-9876543216', NULL, NULL, 8, 19),

-- Management Studies Equipment
('Interactive Whiteboard', 'https://example.com/images/whiteboard.jpg', 'Business Lab, D-301', 'Available', 'Medium', 8, 'Prof. Patricia Garcia', 'Business Lab Incharge', 'patricia.garcia@college.edu', '+91-9876543217', NULL, NULL, 9, 20)
ON CONFLICT DO NOTHING;

-- ============================================
-- INSERT TICKETS (10 tickets)
-- ============================================

INSERT INTO tickets (name, description, status, urgency, requester_name, assigned_to, department_id, equipment_id) VALUES
('Printer Paper Jam Issue', 'HP LaserJet Pro Printer in Computer Lab 1 is showing paper jam error. Unable to print documents.', 'Pending', 'High', 'Alice Johnson', 'Dr. John Smith', 1, 2),
('Oscilloscope Calibration Needed', 'Oscilloscope in Physics Lab 1 needs calibration. Readings are not accurate.', 'Active', 'Medium', 'Bob Miller', 'Dr. Sarah Williams', 3, 5),
('Microscope Light Not Working', 'Microscope in Biology Lab has a faulty light source. Need replacement bulb.', 'Pending', 'High', 'Carol White', 'Dr. Emily Davis', 5, 11),
('3D Printer Filament Runout', '3D Printer in CAD Lab has run out of PLA filament. Need to refill.', 'Active', 'Low', 'David Lee', 'Prof. Robert Taylor', 6, 16),
('Network Switch Port Failure', 'Port 12 on Cisco Network Switch in Server Room is not working. Need replacement.', 'Pending', 'Critical', 'Frank Wilson', 'Dr. John Smith', 1, 3),
('Centrifuge Speed Issue', 'Centrifuge machine in Microbiology Lab is not reaching required RPM. Motor may need servicing.', 'Active', 'Medium', 'Grace Chen', 'Dr. Emily Davis', 5, 12),
('Power Supply Overheating', 'Power Supply Unit in Electrical Lab is overheating during extended use. Safety concern.', 'Pending', 'Critical', 'Henry Brown', 'Dr. Lisa Anderson', 7, 17),
('Projector Lamp Replacement', 'Projector in Project Lab needs lamp replacement. Current lamp is dim.', 'Active', 'Low', 'Ivy Taylor', 'Dr. John Smith', 1, 4),
('Autoclave Door Seal Leak', 'Autoclave in Microbiology Lab has a leaking door seal. Needs immediate repair.', 'Pending', 'High', 'Jack Martinez', 'Dr. Emily Davis', 5, 13),
('UTM Calibration Due', 'Universal Testing Machine in Civil Engineering Lab is due for annual calibration.', 'Active', 'Medium', 'Kate Garcia', 'Dr. James Martinez', 8, 19)
ON CONFLICT DO NOTHING;

-- ============================================
-- VERIFY DATA
-- ============================================

SELECT 'Buildings' as table_name, COUNT(*) as count FROM buildings
UNION ALL
SELECT 'Departments', COUNT(*) FROM departments
UNION ALL
SELECT 'Rooms', COUNT(*) FROM rooms
UNION ALL
SELECT 'Equipment', COUNT(*) FROM equipment
UNION ALL
SELECT 'Tickets', COUNT(*) FROM tickets;

