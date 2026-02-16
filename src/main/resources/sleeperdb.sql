CREATE TABLE bench_mould_inspection (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    line_shed_no VARCHAR(50),

    checking_date DATE,

    bench_gang_no VARCHAR(50),

    sleeper_type VARCHAR(50),

    latest_casting_date DATE,

    bench_visual_result VARCHAR(100),
    bench_dimensional_result VARCHAR(100),

    mould_visual_result VARCHAR(100),
    mould_dimensional_result VARCHAR(100),

    combined_remarks TEXT,

    created_by VARCHAR(50),
    updated_by VARCHAR(50),

    created_date DATETIME,
    updated_date DATETIME,

    status VARCHAR(10)
);
