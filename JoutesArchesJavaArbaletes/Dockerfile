FROM ghcr.io/google/nsjail/nsjail@sha256:1fa97d29c55f8cecedd03b85d2b42c88cb9c1d5666da66e2058c66a01da85f58

RUN apt-get -y update && apt-get install -y \
    openjdk-17-jre-headless \
    && rm -rf /var/lib/apt/lists/*

RUN mkdir /app
WORKDIR /app
COPY challenge/ /app/

EXPOSE 1337

# We need to change rlimit_as (max virtual mem) as the default is not enough for java to start
ENTRYPOINT nsjail -Ml \
            --port 1337 \
            --user nobody:nobody \
            --group nogroup:nogroup \
            --cwd /app \
            --rlimit_as 7000 \
            --hostname localhost \
            --max_conns_per_ip 1 \
            -R /app \
            -R /lib \
            -R /lib64 \
            -R /usr/lib \
            -T /tmp \
            --disable_clone_newnet \
            --time_limit 600 \
            --  /usr/lib/jvm/java-17-openjdk-amd64/bin/java -Dctf404.flag.cjgJF4GCxj2QD5Lg=404CTF{j4v4_3s7_c00l_m41s_zes7_m1euX_Qu4nd_c3z7_z4f3} -jar /app/server.jar
