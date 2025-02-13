echo "Pulling Clickhouse image"
docker run -d --name clickhouse-server -p 9000:9000 -p 8123:8123 clickhouse/clickhouse-server:latest

echo "Waiting for ClickHouse server to start [3 Sec]..."
sleep 3

echo "Copying init.sql file into Clickhouse image"
docker cp init.sql clickhouse-server:/init.sql

echo "Executing DDL init.sql on ClickHouse"
docker exec -it clickhouse-server clickhouse-client --queries-file=/init.sql

printf "\n--------- Company Name, Employee Avg Revenue: \n"
docker exec -it clickhouse-server clickhouse-client --query "
SELECT name, avg(revenue / employees) AS avg_revenue
  FROM analytics.company
WHERE employees >= 50 GROUP BY name
ORDER BY avg_revenue DESC;
"

printf "\n--------- Company Name, Highest Revenue per Employee: \n"
docker exec -it clickhouse-server clickhouse-client --query "
SELECT name, avg(revenue / employees) AS rpe FROM analytics.company
GROUP BY name ORDER BY rpe DESC LIMIT 1;
"

echo "Stop and remove image"
docker rm -f clickhouse-server > /dev/null
