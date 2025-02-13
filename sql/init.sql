-- This is Clickhouse SQL dialect:  script inits tables with data on standalone (non-distributed) server
CREATE DATABASE IF NOT EXISTS analytics;

-- Create Company table with MergeTree engine, partitioned by insertion time
CREATE TABLE IF NOT EXISTS analytics.company (
                                                   name        String,
                                                   revenue     UInt64,
                                                   employees   UInt32,
                                                   saved_at    DateTime DEFAULT now(),
) ENGINE = MergeTree()
  PARTITION BY toYYYYMMDD(saved_at)
  ORDER BY (name);

-- Insert sample data
INSERT INTO analytics.company (name, revenue, employees)
VALUES ('TechCorp', 1000000, 100);

INSERT INTO analytics.company (name, revenue, employees)
VALUES ('InnovateLtd', 500000, 50);

INSERT INTO analytics.company (name, revenue, employees)
VALUES ('AlphaInc', 700000, 75);

INSERT INTO analytics.company (name, revenue, employees)
VALUES ('BetaLLC', 200000, 30);
