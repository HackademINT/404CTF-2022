#!/bin/bash

mkdir /sys/fs/cgroup/{cpu,memory,pids}/NSJAIL

nsjail \
    -Ml --port 4444 \
    --user nobody:nobody \
    --group nogroup:nogroup \
    --max_conns_per_ip 3 \
    --hostname localhost \
    --cwd /app \
    -R /app/recrutement \
    -R /app/flag.txt \
    -R /bin \
    -R /lib \
    -R /lib64 \
    --time_limit 30 \
    --cgroup_pids_max 4 \
    --cgroup_mem_max 67108864 \
    --cgroup_cpu_ms_per_sec 100 \
    -- $@ 

