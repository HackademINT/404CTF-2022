#!/bin/bash

mkdir /sys/fs/cgroup/{cpu,memory,pids}/NSJAIL

nsjail \
    -Ml --port 4444 \
    --user nobody:nobody \
    --group nogroup:nogroup \
    --max_conns_per_ip 3 \
    --cwd /app \
    -R /app/cache_cache \
    -R /app/flag.txt \
    --time_limit 30 \
    --cgroup_pids_max 1 \
    --cgroup_mem_max 67108864 \
    --cgroup_cpu_ms_per_sec 100 \
    -- $@
